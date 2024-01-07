package ru.ilot.ilottower.logic.command.dungeon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ilot.ilottower.model.entities.dungeon.Dungeon;
import ru.ilot.ilottower.model.entities.dungeon.DungeonParty;
import ru.ilot.ilottower.model.entities.dungeon.DungeonPartyPlayer;
import ru.ilot.ilottower.model.entities.user.Player;
import ru.ilot.ilottower.model.enums.StateOfPlayer;
import ru.ilot.ilottower.model.repository.dungeon.DungeonPartyPlayerRepository;
import ru.ilot.ilottower.model.repository.dungeon.DungeonPartyRepository;
import ru.ilot.ilottower.model.repository.dungeon.DungeonRepository;
import ru.ilot.ilottower.model.repository.user.PlayerRepository;
import ru.ilot.ilottower.telegram.exception.NoRegisteredUserException;
import ru.ilot.ilottower.telegram.response.StringResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreatePartyService {
    private final PlayerRepository playerRepository;
    private final DungeonPartyRepository dungeonPartyRepository;
    private final DungeonRepository dungeonRepository;
    private final DungeonPartyPlayerRepository dungeonPartyPlayerRepository;

    public StringResponse createParty(long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(NoRegisteredUserException::new);
        if (player.getState() != StateOfPlayer.IDLE) {
            return new StringResponse("Сначала доделай текущее дело!");
        }

        Optional<DungeonPartyPlayer> optionalDungeonPartyPlayer = dungeonPartyPlayerRepository.findFirstByPlayerId(playerId);
        if (optionalDungeonPartyPlayer.isPresent()) {
            return new StringResponse("Ты уже в команде!");
        }

        Optional<Dungeon> optionalDungeon = dungeonRepository.findFirstByLocationId(player.getLocation().getId());
        if (optionalDungeon.isEmpty()) {
            return new StringResponse("Нельзя создать команду там, где нет данжа!");
        }

        Dungeon dungeon = optionalDungeon.get();

        List<DungeonPartyPlayer> dungeonPartyPlayers = new ArrayList<>();
        DungeonPartyPlayer dungeonPartyPlayer = new DungeonPartyPlayer();
        dungeonPartyPlayer.setPlayer(player);
        dungeonPartyPlayers.add(dungeonPartyPlayer);

        DungeonParty newParty = new DungeonParty();
        newParty.setLeader(player);
        newParty.setDungeon(dungeon);
        newParty.setInviteOnly(true);
        newParty.setEntered(false);
        newParty.setInviteList(new ArrayList<>());
        newParty.setPlayers(dungeonPartyPlayers);

        dungeonPartyPlayer.setParty(newParty);

        dungeonPartyRepository.save(newParty);
        StringBuilder sb = new StringBuilder();
        sb.append("Команда успешно создана!").append("\n");
        sb.append("Собирай остальных участников и готовьтесь к походу в данж!").append("\n");
        sb.append("Если что-то не знаешь - не бойся подсмотреть в /helpParties список команд!").append("\n");
        return new StringResponse(sb.toString());
    }
}
