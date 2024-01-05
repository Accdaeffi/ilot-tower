package ru.ilot.ilottower.telegram;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.ilot.ilottower.telegram.commands.AbsCommand;
import ru.ilot.ilottower.telegram.commands.MoveCommand;
import ru.ilot.ilottower.telegram.commands.SendProfileCommand;
import ru.ilot.ilottower.telegram.commands.ShowLocationCommand;

import java.util.Optional;

@Service
@Slf4j
public class MessageParser implements ApplicationContextAware {

    @Autowired
    ApplicationContext appContext;

    /**
     * Decide, which message was sent and execute necessary operations. Main method
     * of the class.
     */
    public Optional<AbsCommand> parseMessage(@NonNull String messageText, @NonNull User messageAuthor, @NonNull Long chatId) {

        try {
            String[] arr = messageText.split("_", 2);
            String command = arr[0];
            if (command.contains("@")) {
                command = arr[0].substring(0, arr[0].indexOf("@"));
            }
            String argument = (arr.length > 1) ? arr[1] : null;

            AbsCommand commandHandler = null;

            switch (command) {
                case "\uD83D\uDCA1 Герой":
                case "/me": {
                    commandHandler = appContext.getBean(SendProfileCommand.class, messageAuthor.getId());
                }
                break;
                case "⬆️ Север":
                case "⬅️ Запад":
                case "➡️ Восток":
                case "⬇️ Юг": {
                    commandHandler = appContext.getBean(MoveCommand.class, messageAuthor.getId(), command);
                }
                break;
                case "🔍 Осмотреться": {
                    commandHandler = appContext.getBean(ShowLocationCommand.class, messageAuthor.getId());
                }
                break;
            }

            return Optional.ofNullable(commandHandler);
        } catch (Exception ex) {
            log.error("Error during parsing command {}!", messageText, ex);
            return Optional.empty();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }
}
