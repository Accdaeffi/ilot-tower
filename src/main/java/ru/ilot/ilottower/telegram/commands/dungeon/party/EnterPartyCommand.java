package ru.ilot.ilottower.telegram.commands.dungeon.party;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.ilot.ilottower.logic.command.dungeon.party.EnterPartyService;
import ru.ilot.ilottower.telegram.commands.AbsCommand;
import ru.ilot.ilottower.telegram.response.Response;
import ru.ilot.ilottower.telegram.response.StringResponse;

@Service
@Scope("prototype")
public class EnterPartyCommand extends AbsCommand {

    private final long userId;
    private final String targetPartyIdString;

    @Autowired
    private EnterPartyService enterPartyService;

    public EnterPartyCommand(long userId, String argument) {
        this.userId = userId;
        this.targetPartyIdString = argument;
    }

    @Override
    public Response<?> execute() {
        try {
            int targetPartyId = Integer.parseInt(targetPartyIdString);
            return enterPartyService.enterParty(userId, targetPartyId);
        } catch (NumberFormatException ex) {
            return new StringResponse("Неверный номер команды!");
        }
    }
}
