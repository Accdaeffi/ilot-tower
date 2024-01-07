package ru.ilot.ilottower.telegram.commands.dungeon.party;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.ilot.ilottower.logic.command.dungeon.party.LeavePartyService;
import ru.ilot.ilottower.telegram.commands.AbsCommand;
import ru.ilot.ilottower.telegram.response.Response;

@Service
@Scope("prototype")
public class LeavePartyCommand extends AbsCommand {

    private final long userId;

    @Autowired
    private LeavePartyService leavePartyService;

    public LeavePartyCommand(long userId) {
        this.userId = userId;
    }

    @Override
    public Response<?> execute() {
        return leavePartyService.leaveParty(userId);
    }
}
