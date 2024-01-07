package ru.ilot.ilottower.telegram.commands.dungeon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.ilot.ilottower.logic.command.dungeon.ViewMyPartyService;
import ru.ilot.ilottower.telegram.commands.AbsCommand;
import ru.ilot.ilottower.telegram.response.Response;

@Service
@Scope("prototype")
public class ViewMyPartyCommand extends AbsCommand {

    private final long userId;

    @Autowired
    private ViewMyPartyService viewMyPartyService;

    public ViewMyPartyCommand(long userId) {
        this.userId = userId;
    }

    @Override
    public Response<?> execute() {
        return viewMyPartyService.viewMyParty(userId);
    }
}
