package ru.ilot.ilottower.telegram.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.ilot.ilottower.logic.command.user.GetProfileTextService;
import ru.ilot.ilottower.telegram.keyboard.GetReplyKeyboardByState;
import ru.ilot.ilottower.telegram.response.Response;
import ru.ilot.ilottower.telegram.response.StringResponse;
import ru.ilot.ilottower.telegram.response.StringWithKeyboardResponse;

@Service
@Scope(scopeName = "prototype")
public class SendProfileCommand extends AbsCommand {

    @Autowired
    GetProfileTextService getProfileTextService;

    private final Long userId;

    public SendProfileCommand(Long userId) {
        this.userId = userId;
    }

    @Override
    public Response<?> execute() {
        return getProfileTextService.getProfile(userId);
    }
}
