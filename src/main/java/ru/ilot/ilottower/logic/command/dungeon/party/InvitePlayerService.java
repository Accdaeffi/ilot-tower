package ru.ilot.ilottower.logic.command.dungeon.party;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilot.ilottower.logic.util.MessageSender;
import ru.ilot.ilottower.model.entities.dungeon.Dungeon;
import ru.ilot.ilottower.model.entities.dungeon.DungeonParty;
import ru.ilot.ilottower.model.entities.dungeon.DungeonPartyInvitation;
import ru.ilot.ilottower.model.entities.dungeon.DungeonPartyPlayer;
import ru.ilot.ilottower.model.entities.user.Player;
import ru.ilot.ilottower.model.repository.dungeon.DungeonPartyRepository;
import ru.ilot.ilottower.model.repository.user.PlayerRepository;
import ru.ilot.ilottower.telegram.response.StringResponse;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvitePlayerService {
    private final PlayerRepository playerRepository;
    private final DungeonPartyRepository dungeonPartyRepository;
    private final MessageSender messageSender;

    @Transactional
    public StringResponse invitePlayer(long playerId, long targetPlayerId) {
        Optional<Player> optionalTargetPlayer = playerRepository.findById(targetPlayerId);
        if (optionalTargetPlayer.isEmpty()) {
            return new StringResponse("Нет такого игрока!");
        }
        Player targetPlayer = optionalTargetPlayer.get();

        Optional<DungeonParty> optionalDungeonParty = dungeonPartyRepository.findFirstByLeaderId(playerId);

        if (optionalDungeonParty.isEmpty()) {
            return new StringResponse("А ты точно лидер команды?");
        }
        DungeonParty dungeonParty = optionalDungeonParty.get();

        if (dungeonParty.isEntered()) {
            return new StringResponse("Поздно кидать приглашения!");
        }

        if (dungeonParty.getInviteList().size() >= 7) {
            return new StringResponse("В команде максимум 5 человек, а ты уже послал 7 инвайтов. Не многовато-ли?");
        }

        if (dungeonParty.getInviteList().stream().map(DungeonPartyInvitation::getPlayer).toList().contains(targetPlayer)) {
            return new StringResponse("Этому игроку уже отправлено приглашение!");
        }

        if (dungeonParty.getPlayers().stream().map(DungeonPartyPlayer::getPlayer).toList().contains(targetPlayer)) {
            return new StringResponse("Этот игрок уже в вашей команде!");
        }

        Dungeon dungeon = dungeonParty.getDungeon();
        try {
            DungeonPartyInvitation dungeonPartyInvitation = new DungeonPartyInvitation();
            dungeonPartyInvitation.setPlayer(targetPlayer);
            dungeonPartyInvitation.setParty(dungeonParty);

            dungeonParty.getInviteList().add(dungeonPartyInvitation);
            dungeonPartyRepository.save(dungeonParty);

            StringBuilder sb = new StringBuilder();
            sb.append(STR."Тебя пригласили в команду, которая готовится зайти в подземелье \{dungeon.getName()}!").append("\n");
            sb.append(STR."Чтобы принять приглашение, подойди в точку \{dungeon.getLocation().getId()} и введи /enterParty_\{dungeonParty.getId()}");
            StringResponse message = new StringResponse(sb.toString());
            messageSender.sendMessage(message, targetPlayerId);
            return new StringResponse("Приглашение отправлено!");
        } catch (Exception ex) {
            log.error("Error during inviting player!", ex);
            return new StringResponse("Приглашение отправить не удалось!");
        }
    }
}
