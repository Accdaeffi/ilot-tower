package ru.ilot.ilottower.logic.command.dungeon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ilot.ilottower.logic.util.MessageSender;
import ru.ilot.ilottower.model.entities.dungeon.DungeonParty;
import ru.ilot.ilottower.model.entities.dungeon.DungeonPartyPlayer;
import ru.ilot.ilottower.telegram.response.Response;

@Slf4j
@Service
@RequiredArgsConstructor
public class DungeonUtil {

    private final MessageSender messageSender;

    void broadcastMessage(DungeonParty dungeonParty, Response<?> message) {
        for (DungeonPartyPlayer participant : dungeonParty.getPlayers()) {
            Long targetId = participant.getPlayer().getId();
            messageSender.sendMessage(message, targetId);
        }
    }
}
