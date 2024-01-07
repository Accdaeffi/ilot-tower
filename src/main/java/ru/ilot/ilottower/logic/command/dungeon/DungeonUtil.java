package ru.ilot.ilottower.logic.command.dungeon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.ilot.ilottower.model.entities.dungeon.DungeonParty;
import ru.ilot.ilottower.model.entities.dungeon.DungeonPartyPlayer;
import ru.ilot.ilottower.telegram.response.Response;

@Slf4j
@Service
@RequiredArgsConstructor
public class DungeonUtil {

    private final AbsSender absSender;

    void broadcastMessage(DungeonParty dungeonParty, Response<?> message) {
        for (DungeonPartyPlayer participant : dungeonParty.getPlayers()) {
            Long targetId = participant.getPlayer().getId();
            try {
                message.send(absSender, targetId);
            } catch (Exception ex) {
                log.warn("Ошибка отправки {} сообщения \"{}\" о покидании команды {}!", targetId, message.getContent().toString(), dungeonParty.getId());
            }
        }

    }
}
