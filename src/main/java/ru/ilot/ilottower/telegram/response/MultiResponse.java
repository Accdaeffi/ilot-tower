package ru.ilot.ilottower.telegram.response;

import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class MultiResponse extends Response<List<Response<?>>> {

	public MultiResponse(List<Response<?>> responseContent) {
		super(responseContent);
	}

	@Override
	public void send(AbsSender sender, Long chatId) throws TelegramApiException {
		for (Response<?> response : responseContent) {
			response.send(sender, chatId);
		}

	}

}
