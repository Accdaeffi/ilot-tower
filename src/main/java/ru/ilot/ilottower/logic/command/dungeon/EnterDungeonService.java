package ru.ilot.ilottower.logic.command.dungeon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.ilot.ilottower.logic.async.dungeon.SendMessageToPlayerAsync;
import ru.ilot.ilottower.logic.util.DungeonGenerator;
import ru.ilot.ilottower.model.entities.dungeon.Dungeon;
import ru.ilot.ilottower.model.entities.dungeon.DungeonCell;
import ru.ilot.ilottower.model.entities.dungeon.DungeonInstance;
import ru.ilot.ilottower.model.entities.dungeon.DungeonParty;
import ru.ilot.ilottower.model.entities.dungeon.DungeonPartyPlayer;
import ru.ilot.ilottower.model.entities.user.Player;
import ru.ilot.ilottower.model.enums.StateOfPlayer;
import ru.ilot.ilottower.model.enums.dungeon.DungeonCellType;
import ru.ilot.ilottower.model.enums.geo.BuildingType;
import ru.ilot.ilottower.model.repository.dungeon.DungeonCellRepository;
import ru.ilot.ilottower.model.repository.dungeon.DungeonInstanceRepository;
import ru.ilot.ilottower.model.repository.dungeon.DungeonPartyRepository;
import ru.ilot.ilottower.model.repository.user.PlayerRepository;
import ru.ilot.ilottower.telegram.exception.DataLogicException;
import ru.ilot.ilottower.telegram.exception.NoRegisteredUserException;
import ru.ilot.ilottower.telegram.keyboard.MiniReplyKeyboardGetter;
import ru.ilot.ilottower.telegram.response.NullResponse;
import ru.ilot.ilottower.telegram.response.Response;
import ru.ilot.ilottower.telegram.response.StringResponse;
import ru.ilot.ilottower.telegram.response.StringWithKeyboardResponse;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnterDungeonService implements ApplicationContextAware {
    private final PlayerRepository playerRepository;
    private final DungeonPartyRepository dungeonPartyRepository;
    private final MiniReplyKeyboardGetter miniReplyKeyboardGetter;
    private final DungeonCellRepository dungeonCellRepository;
    private final DungeonInstanceRepository dungeonInstanceRepository;
    private final DungeonUtil dungeonUtil;
    private final VisionService visionService;
    private ApplicationContext appContext;

    @Transactional
    public Response<?> enterDungeon(long playerId) {
        try {
            Player player = playerRepository.findById(playerId).orElseThrow(NoRegisteredUserException::new);
            if (player.getState() != StateOfPlayer.IDLE) {
                return new StringResponse("Сначала доделай текущее дело!");
            }

            Optional<DungeonParty> optionalDungeonParty = dungeonPartyRepository.findFirstByLeaderId(playerId);
            if (optionalDungeonParty.isEmpty()) {
                return new StringResponse("А ты точно лидер команды?");
            }
            DungeonParty party = optionalDungeonParty.get();

            if (party.isEntered()) {
                return new StringResponse("А вы собстна уже зашли.");
            }

            Dungeon dungeon = party.getDungeon();

            List<Player> participants = party.getPlayers().stream().map(DungeonPartyPlayer::getPlayer).toList();
            if (participants.stream().anyMatch(p -> !p.getLocation().equals(dungeon.getLocation()))) {
                return new StringResponse("Ещё не вся группа собралась!");
            }

            log.info("Start generation dungeon");

            DungeonGenerator generator = appContext.getBean(DungeonGenerator.class, party, dungeon);
            DungeonInstance instance = generator.generateDungeon();

            dungeonInstanceRepository.save(instance);

            log.info("End generation dungeon");

            DungeonCell firstCell = instance.getRooms().stream().filter(cell -> cell.getCellType().equals(DungeonCellType.ENTER)).findFirst()
                    .orElseThrow(() -> new DataLogicException(STR."No entry point in dungeon \{instance.getId()}!"));
            for (DungeonPartyPlayer member : party.getPlayers()) {
                member.setDungeonCell(firstCell);
            }

            for (Player member : participants) {
                member.setState(StateOfPlayer.DUNGEONS);
                member.setBuildingLocation(BuildingType.DUNGEON);
            }

            party.setEntered(true);

            dungeonPartyRepository.save(party);

            ReplyKeyboard miniReplyKeyboard = miniReplyKeyboardGetter.getKeyboard();
            StringWithKeyboardResponse stringWithKeyboardResponse = new StringWithKeyboardResponse("Добро пожаловать в подземелье!", miniReplyKeyboard);
            dungeonUtil.broadcastMessage(party, stringWithKeyboardResponse);

            for (DungeonPartyPlayer member : party.getPlayers()) {
                SendMessageToPlayerAsync sendMessageToPlayerAsync = appContext.getBean(SendMessageToPlayerAsync.class, member.getPlayer().getId(), visionService.getVision(instance, member));
                Thread.startVirtualThread(sendMessageToPlayerAsync);
            }

            return new NullResponse();
        } catch (Exception ex) {
            log.error("Ошибка при входе в подземелье!", ex);
            return new StringResponse("Ошибка при входе в подземелье!");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }
}
