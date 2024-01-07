package ru.ilot.ilottower.telegram.response;

import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class NullResponse extends Response<Void> {
    public NullResponse() {
        super(null);
    }

    @Override
    public void send(AbsSender sender, Long chatId) throws TelegramApiException {

    }
}
