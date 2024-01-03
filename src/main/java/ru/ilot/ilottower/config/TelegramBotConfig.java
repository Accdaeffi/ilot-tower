package ru.ilot.ilottower.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.ilot.ilottower.telegram.Bot;
import ru.ilot.ilottower.telegram.CallbackParser;
import ru.ilot.ilottower.telegram.MessageParser;
import ru.ilot.ilottower.telegram.PhotoParser;

@Slf4j
@Configuration
public class TelegramBotConfig {

	@Value("${telegram.bot.username}")
	String botUsername;

	@Value("${telegram.bot.token}")
	String botToken;

	@Bean
	public Bot bot(MessageParser messageParser, CallbackParser callbackParser, PhotoParser photoParser)
			throws Exception {
		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			Bot bot = new Bot(botUsername, botToken, messageParser, photoParser, callbackParser);

			botsApi.registerBot(bot);

			log.info("Bot started!");

			return bot;
		} catch (TelegramApiException e) {
			log.error("Critical error!", e);
			throw new Exception();
		}
	}

}
