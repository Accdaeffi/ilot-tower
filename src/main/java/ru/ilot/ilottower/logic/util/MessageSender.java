package ru.ilot.ilottower.logic.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.ilot.ilottower.telegram.response.Response;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageSender {
    private final AbsSender absSender;

    public void sendMessage(Response<?> message, long targetId) {
        try {
            message.send(absSender, targetId);
        } catch (Exception ex) {
            log.warn("Ошибка отправки {} сообщения \"{}\"!", targetId, message.getContent().toString());
        }
    }
}
