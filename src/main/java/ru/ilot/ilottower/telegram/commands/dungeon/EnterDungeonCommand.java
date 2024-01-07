package ru.ilot.ilottower.telegram.commands.dungeon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.ilot.ilottower.logic.command.dungeon.EnterDungeonService;
import ru.ilot.ilottower.logic.command.dungeon.party.LeavePartyService;
import ru.ilot.ilottower.telegram.commands.AbsCommand;
import ru.ilot.ilottower.telegram.response.Response;

@Service
@Scope("prototype")
public class EnterDungeonCommand extends AbsCommand {

    private final long userId;

    @Autowired
    private EnterDungeonService enterDungeon;

    public EnterDungeonCommand(long userId) {
        this.userId = userId;
    }

    @Override
    public Response<?> execute() {
        return enterDungeon.enterDungeon(userId);
    }
}
