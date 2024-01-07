package ru.ilot.ilottower.telegram.commands.dungeon;

import org.springframework.stereotype.Service;
import ru.ilot.ilottower.telegram.commands.AbsCommand;
import ru.ilot.ilottower.telegram.response.Response;
import ru.ilot.ilottower.telegram.response.StringResponse;

@Service
public class HelpPartiesCommand extends AbsCommand {
    @Override
    public Response<?> execute() {
        String help = """
                /createParty - только в локации с данжем - создаёт команду на покорение подземелья
                /viewParties - только в локации с данжем - позволяет посмотреть имеющиеся команды
                /enterParty_{id команды} - только в локе с данжем - позволяет войти в состав выбранной команды
                
                /leaveParty - где угодно - позволяет покинуть команду (но не в данже)
                /viewMyParty - где угодно - позволяет посмотреть, как дела у команды, в которой ты состоишь
                
                /inviteParty_{id игрока} - где угодно, только лидер - позволяет пригласить игрока в команду
                /inviteOnlyParty_true(_false) - где угодно, только лидер - принимаются ли в команду только по приглашению или же все подряд
                
                /sendMessageParty {сообщение}- где угодно, только лидер - рассылка сообщения по всей команде
                /enterDungeon - только лидер, только у данжа - войти в поземелье (кого не будет на месте - вылетит из команды)
                """;
        return new StringResponse(help);
    }
}
