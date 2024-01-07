package ru.ilot.ilottower.telegram.commands.dungeon;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.ilot.ilottower.logic.command.dungeon.CreatePartyService;
import ru.ilot.ilottower.telegram.commands.AbsCommand;
import ru.ilot.ilottower.telegram.response.Response;

@Service
@Scope("prototype")
public class CreatePartyCommand extends AbsCommand {
    private final long userId;

    @Autowired
    private CreatePartyService createPartyService;

    public CreatePartyCommand(long userId) {
        this.userId = userId;
    }

    @Override
    public Response<?> execute() {
        return createPartyService.createParty(userId);
    }
}
