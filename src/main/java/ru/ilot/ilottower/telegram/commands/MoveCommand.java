package ru.ilot.ilottower.telegram.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.ilot.ilottower.logic.command.user.MovementService;
import ru.ilot.ilottower.telegram.response.Response;

@Service
@Scope("prototype")
public class MoveCommand extends AbsCommand {

    private final Long userId;
    private final String commandText;

    @Autowired
    private MovementService movementService;

    public MoveCommand(Long userId, String commandText) {
        this.userId = userId;
        this.commandText = commandText;
    }

    @Override
    public Response<?> execute() {
        return movementService.move(userId, commandText);
    }
}
