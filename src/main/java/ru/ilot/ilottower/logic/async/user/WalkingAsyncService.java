package ru.ilot.ilottower.logic.async.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.ilot.ilottower.model.entities.geo.Location;
import ru.ilot.ilottower.model.entities.user.Player;
import ru.ilot.ilottower.model.enums.StateOfPlayer;
import ru.ilot.ilottower.model.repository.geo.LocationRepository;
import ru.ilot.ilottower.model.repository.user.PlayerRepository;
import ru.ilot.ilottower.telegram.exception.DataLogicException;
import ru.ilot.ilottower.telegram.exception.NoRegisteredUserException;
import ru.ilot.ilottower.telegram.keyboard.IdleReplyKeyboardGetter;
import ru.ilot.ilottower.telegram.response.StringWithKeyboardResponse;

@Slf4j
@Service
@Scope("prototype")
@RequiredArgsConstructor
public class WalkingAsyncService extends Thread {

    private final long userId;
    private final String locationId;

    private final long walkTimeInMilliSeconds;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private IdleReplyKeyboardGetter idleReplyKeyboardGetter;

    @Autowired
    private AbsSender sender;

    @Override
    public void run() {
        try {
            Thread.sleep(walkTimeInMilliSeconds);

            StringBuilder sb = new StringBuilder();
            sb.append(getEndOfPath(userId)).append("\n");
            // TODO return daily quest
            //sb.append(SubQuestNpc.increaseProgress(user, QuestTaskType.Walk, 0, telegramBotClient));

            StringWithKeyboardResponse response = new StringWithKeyboardResponse(sb.toString(),
                    idleReplyKeyboardGetter.getKeyboard());
            try {
                // TODO подумать о другом определении получателя
                response.send(sender, userId);
            } catch (Exception ex) {
                log.error("Error during sending response", ex);
            }
        } catch (InterruptedException e) {
            log.error("Waiting exception!", e);
        } finally {
            Player player = playerRepository.findById(userId).orElseThrow(NoRegisteredUserException::new);
            Location location = locationRepository.findById(locationId).orElseThrow(() -> new DataLogicException(STR."No location with id \{locationId}"));
            player.setState(StateOfPlayer.IDLE);
            player.setLocation(location);
            playerRepository.save(player);
        }
    }

    private String getEndOfPath(long userId) {
        // TODO вернуть вывод
        //string result = TextGenerator.EndOfWalk(userId);
        return "Дошёл";
    }
}
