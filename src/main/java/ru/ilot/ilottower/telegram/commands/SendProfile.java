package ru.ilot.ilottower.telegram.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.ilot.ilottower.logic.GetProfileTextService;
import ru.ilot.ilottower.telegram.response.Response;
import ru.ilot.ilottower.telegram.response.StringResponse;

@Service
@Scope(scopeName = "prototype")
public class SendProfile extends AbsCommand {

    @Autowired
    GetProfileTextService getProfileTextService;

    private final Long userId;

    public SendProfile(Long userId) {
        this.userId = userId;
    }

    @Override
    public Response<?> execute() {
        return new StringResponse(getProfileTextService.getProfile(userId));
    }
}
