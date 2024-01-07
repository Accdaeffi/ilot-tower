package ru.ilot.ilottower.logic.async.dungeon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.ilot.ilottower.telegram.response.Response;

@Slf4j
@Service
@Scope("prototype")
@RequiredArgsConstructor
public class SendMessageToPlayerAsync extends Thread {
    private final long userId;
    private final Response<?> response;

    @Autowired
    private AbsSender sender;

    @Override
    public void run() {
        try {
            // TODO подумать о другом определении получателя
            response.send(sender, userId);
        } catch (Exception ex) {
            log.error("Ошибка при отправке сообщения игроку {}!", userId, ex);
        }
    }
}
