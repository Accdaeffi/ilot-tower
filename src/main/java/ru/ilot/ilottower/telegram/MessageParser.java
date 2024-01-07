package ru.ilot.ilottower.telegram;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.ilot.ilottower.telegram.commands.AbsCommand;
import ru.ilot.ilottower.telegram.commands.MoveCommand;
import ru.ilot.ilottower.telegram.commands.SendProfileCommand;
import ru.ilot.ilottower.telegram.commands.ShowLocationCommand;
import ru.ilot.ilottower.telegram.commands.dungeon.CreatePartyCommand;
import ru.ilot.ilottower.telegram.commands.dungeon.HelpPartiesCommand;
import ru.ilot.ilottower.telegram.commands.dungeon.LeavePartyCommand;
import ru.ilot.ilottower.telegram.commands.dungeon.ViewPartyCommand;

import java.util.Optional;

@Service
@Slf4j
public class MessageParser implements ApplicationContextAware {

    @Autowired
    ApplicationContext appContext;

    /**
     * Decide, which message was sent and execute necessary operations. Main method
     * of the class.
     */
    public Optional<AbsCommand> parseMessage(@NonNull String messageText, @NonNull User messageAuthor, @NonNull Long chatId) {

        try {
            String[] arr = messageText.split("_", 2);
            String command = arr[0];
            if (command.contains("@")) {
                command = arr[0].substring(0, arr[0].indexOf("@"));
            }
            String argument = (arr.length > 1) ? arr[1] : null;

            AbsCommand commandHandler = null;

            switch (command) {
                case "\uD83D\uDCA1 Герой":
                case "/me": {
                    commandHandler = appContext.getBean(SendProfileCommand.class, messageAuthor.getId());
                }
                break;
                case "⬆️ Север":
                case "⬅️ Запад":
                case "➡️ Восток":
                case "⬇️ Юг": {
                    commandHandler = appContext.getBean(MoveCommand.class, messageAuthor.getId(), command);
                }
                break;
                case "🔍 Осмотреться": {
                    commandHandler = appContext.getBean(ShowLocationCommand.class, messageAuthor.getId());
                }
                break;
                case "/helpParties":
                {
                    commandHandler = appContext.getBean(HelpPartiesCommand.class);
                }
                break;
                case "/createParty":
                {
                    commandHandler = appContext.getBean(CreatePartyCommand.class, messageAuthor.getId());
                }
                break;
                case "/viewParties":
                {
                    commandHandler = appContext.getBean(ViewPartyCommand.class, messageAuthor.getId());
                }
                break;
                case "/leaveParty":
                {
                    commandHandler = appContext.getBean(LeavePartyCommand.class, messageAuthor.getId());
                }
                break;
//
//                case "/enterParty":
//                {
//                    try
//                    {
//                        int partyId = Convert.ToInt32(message.Text.Split('_').Last());
//                        await telegramBotClient.SendTextMessageAsync(playerInfo.Id, PartyFunctions.enterParty(playerInfo, partyId, telegramBotClient), ParseMode.Html);
//                    }
//                    catch (FormatException ex)
//                    {
//                        await telegramBotClient.SendTextMessageAsync(playerInfo.Id, "Неверный номер команды!");
//                    }
//                    catch (OverflowException ex)
//                    {
//                        await telegramBotClient.SendTextMessageAsync(playerInfo.Id, "Неверный номер команды!");
//                    }
//                }
//                break;
//
//
//                case "/inviteParty":
//                {
//                    try
//                    {
//                        int invitedId = Convert.ToInt32(message.Text.Split('_').Last());
//                        await telegramBotClient.SendTextMessageAsync(playerInfo.Id, PartyFunctions.inviteParty(playerInfo, invitedId, telegramBotClient), ParseMode.Html);
//                    }
//                    catch (FormatException ex)
//                    {
//                        await telegramBotClient.SendTextMessageAsync(playerInfo.Id, "Неверный игрок!");
//                    }
//                    catch (OverflowException ex)
//                    {
//                        await telegramBotClient.SendTextMessageAsync(playerInfo.Id, "Слушай, в башню конечно много людей играет, но не настолько много!");
//                    }
//                }
//                break;
//
//                case "/inviteOnlyParty":
//                {
//                    try
//                    {
//                        bool InviteOnly = Convert.ToBoolean(message.Text.Split('_').Last().ToLower());
//                        await telegramBotClient.SendTextMessageAsync(playerInfo.Id, PartyFunctions.setInviteOnlyParty(playerInfo, InviteOnly), ParseMode.Html);
//                    }
//                    catch (FormatException ex)
//                    {
//                        await telegramBotClient.SendTextMessageAsync(playerInfo.Id, "В качества параметра может быть только true или false!");
//                    }
//                }
//                break;
//
//                case "/viewMyParty":
//                {
//                    await telegramBotClient.SendTextMessageAsync(playerInfo.Id, PartyFunctions.viewMyParty(playerInfo), ParseMode.Html);
//                }
//                break;
//                case "/enterDungeon":
//                {
//                    await PartyFunctions.enterDungeon(playerInfo, telegramBotClient);
//                }
//                break;
//
//
//                case "/getMap":
//                {
//                    await telegramBotClient.SendTextMessageAsync(playerInfo.Id, DungeonFunctions.getMap(playerInfo), ParseMode.Html);
//                }
//                break;
            }

            return Optional.ofNullable(commandHandler);
        } catch (Exception ex) {
            log.error("Error during parsing command {}!", messageText, ex);
            return Optional.empty();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }
}
