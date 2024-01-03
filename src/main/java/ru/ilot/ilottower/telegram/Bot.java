package ru.ilot.ilottower.telegram;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ilot.ilottower.telegram.commands.AbsCommand;
import ru.ilot.ilottower.telegram.response.Response;

import java.util.Optional;

@Slf4j
public class Bot extends TelegramLongPollingBot {

	private final MessageParser commandParser;
	private final PhotoParser photoParser;
	private final CallbackParser callbackParser;

	private final String BOT_USERNAME;

	public Bot(String botUserName, String botToken, MessageParser commandParser, PhotoParser photoParser, CallbackParser callbackParser) {
		super(botToken);
		this.BOT_USERNAME = botUserName;
		this.commandParser = commandParser;
		this.photoParser = photoParser;
		this.callbackParser = callbackParser;
	}

	@Override
	public void onUpdateReceived(Update update) {

		if (update.hasMessage()) {

			Message message = update.getMessage();
			Long chatId = message.getChatId();
			User author = message.getFrom();

			log.info("Message!");

			if (message.hasPhoto()) {

				String messageText = message.getCaption();

				/* Parsing command */
				Optional<AbsCommand> optionalCommandHandler = photoParser.parseMessageWithPhoto(message.getPhoto(), messageText,
						author);

				optionalCommandHandler.ifPresent(handler -> {
					try {

						/* Executing command */
						Response<?> result = handler.execute();

						/* Sending result of command */
						result.send(this, chatId);
					} catch (TelegramApiException ex) {
						log.error("Error sending result of command {} from {}!", messageText, author.getId(), ex);
					} catch (Exception ex) {
						log.error("Error during processing command {}!", messageText, ex);
					}
				});

			} else {
				if (message.hasText()) {

					String messageText = message.getText();

					if (messageText.startsWith("/")) {
						String authorId = (author.getUserName() == null) ? author.getFirstName() : author.getUserName();
						log.info("Command {} from {}", messageText, authorId);

						/* Parsing command */
						Optional<AbsCommand> optionalCommandHandler = commandParser.parseMessage(messageText, author, message.getChatId());

						optionalCommandHandler.ifPresent(handler -> {
							try {

								/* Executing command */
								Response<?> result = handler.execute();

								/* Sending result of command */
								result.send(this, chatId);
							} catch (TelegramApiException ex) {
								log.error("Error sending result of command {} from {}!", messageText, author.getId(),
										ex);
							} catch (Exception ex) {
								log.error("Error during processing command {}!", messageText, ex);
							}
						});
					}
				}
			}
		} else {
			if (update.hasCallbackQuery())

			{
				CallbackQuery callback = update.getCallbackQuery();

				String messageText = callback.getData();
				long chatId = callback.getMessage().getChatId();
				int messageId = callback.getMessage().getMessageId();
				User author = callback.getFrom();

				String authorId = (author.getUserName() == null) ? author.getFirstName() : author.getUserName();
				log.info("Callback \"{}\" from {}", messageText, authorId);

				/* Parsing callback */
				Optional<AbsCommand> optionalCallbackHandler = callbackParser.parseCallback(messageText, messageId, author);

				optionalCallbackHandler.ifPresent(handler -> {
					try {

						/* Executing command */
						Response<?> result = handler.execute();

						/* Sending result of command */
						result.send(this, chatId);
					} catch (TelegramApiException ex) {
						log.error("Error sending result of callback {} from {}!", messageText, author.getId(), ex);
					} catch (Exception ex) {
						log.error("Error during processing callback {}!", messageText, ex);
					}
				});
			}
		}
	}

	@Override
	public String getBotUsername() {
		return BOT_USERNAME;
	}


}
