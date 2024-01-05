package ru.ilot.ilottower.telegram.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.ilot.ilottower.logic.command.geo.lookaround.ShowLocationService;
import ru.ilot.ilottower.telegram.response.Response;

@Service
@Scope("prototype")
public class ShowLocationCommand extends AbsCommand {

    @Autowired
    ShowLocationService showLocationService;

    private final Long userId;

    public ShowLocationCommand(Long userId) {
        this.userId = userId;
    }

    @Override
    public Response<?> execute() {
        return showLocationService.showLocation(userId);
    }
}
