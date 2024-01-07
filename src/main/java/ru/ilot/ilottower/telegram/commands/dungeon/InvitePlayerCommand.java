package ru.ilot.ilottower.telegram.commands.dungeon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.ilot.ilottower.logic.command.dungeon.InvitePlayerService;
import ru.ilot.ilottower.telegram.commands.AbsCommand;
import ru.ilot.ilottower.telegram.response.Response;
import ru.ilot.ilottower.telegram.response.StringResponse;

@Service
@Scope("prototype")
public class InvitePlayerCommand extends AbsCommand {

    private final long userId;
    private final String targetUserIdString;

    @Autowired
    private InvitePlayerService invitePlayerService;

    public InvitePlayerCommand(long userId, String argument) {
        this.userId = userId;
        this.targetUserIdString = argument;
    }

    @Override
    public Response<?> execute() {
        try {
            long targetUserId = Long.parseLong(targetUserIdString);
            return invitePlayerService.invitePlayer(userId, targetUserId);
        } catch (NumberFormatException ex) {
            return new StringResponse("Неверный игрок!");
        }
    }
}
