package ru.ilot.ilottower.telegram.commands.dungeon.party;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.ilot.ilottower.logic.command.dungeon.party.MakePartyInviteOnlyService;
import ru.ilot.ilottower.telegram.commands.AbsCommand;
import ru.ilot.ilottower.telegram.response.Response;
import ru.ilot.ilottower.telegram.response.StringResponse;

@Service
@Scope("prototype")
public class MakePartyInviteOnlyCommand extends AbsCommand {

    private final long userId;
    private final String isInviteOnlyString;

    @Autowired
    private MakePartyInviteOnlyService makePartyInviteOnlyService;

    public MakePartyInviteOnlyCommand(long userId, String argument) {
        this.userId = userId;
        this.isInviteOnlyString = argument;
    }

    @Override
    public Response<?> execute() {

        if ("true".equalsIgnoreCase(isInviteOnlyString) || "false".equalsIgnoreCase(isInviteOnlyString)) {
            boolean isInviteOnly = Boolean.parseBoolean(isInviteOnlyString);
            return makePartyInviteOnlyService.makePartyInviteOnly(userId, isInviteOnly);
        } else {
            return new StringResponse("В качества параметра может быть только true или false!");
        }
    }
}
