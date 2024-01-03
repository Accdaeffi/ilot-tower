package ru.ilot.ilottower.telegram;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.ilot.ilottower.telegram.commands.AbsCommand;

import java.util.Optional;

@Service
@Slf4j
public class CallbackParser implements ApplicationContextAware {

    ApplicationContext appContext;

    /**
     * Decide, which message was sent and execute necessary operations. Main method
     * of the class.
     */
    public Optional<AbsCommand> parseCallback(@NonNull String messageText, int messageId, @NonNull User messageAuthor) {

        try {

            String arr[] = messageText.split(" ", 2);
            String command = arr[0];
            String argument = (arr.length > 1) ? arr[1] : null;

            AbsCommand commandHandler = null;

            switch (command) {
                case "checking": {
                //    commandHandler = appContext.getBean(RecordCheckingCommand.class, messageAuthor, argument, messageId);
                }
                break;
                default: {
                    commandHandler = null;
                }
            }

            return Optional.ofNullable(commandHandler);
        } catch (Exception ex) {
            log.error("Error during parsing callback {}!", messageText, ex);
            return Optional.ofNullable(null);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }
}
