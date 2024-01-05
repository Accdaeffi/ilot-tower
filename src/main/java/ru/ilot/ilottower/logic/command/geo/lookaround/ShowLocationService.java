package ru.ilot.ilottower.logic.command.geo.lookaround;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.ilot.ilottower.logic.util.GetBackgroundImageService;
import ru.ilot.ilottower.model.entities.user.Player;
import ru.ilot.ilottower.model.repository.user.PlayerRepository;
import ru.ilot.ilottower.telegram.exception.NoRegisteredUserException;
import ru.ilot.ilottower.telegram.keyboard.GetReplyKeyboardByState;
import ru.ilot.ilottower.telegram.response.PhotoResponse;
import ru.ilot.ilottower.telegram.response.Response;
import ru.ilot.ilottower.telegram.response.StringResponse;
import ru.ilot.ilottower.telegram.response.StringWithKeyboardResponse;

import java.io.InputStream;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShowLocationService {
    private final PlayerRepository playerRepository;
    private final GetBackgroundImageService getBackgroundImageService;
    private final GetReplyKeyboardByState getReplyKeyboardByState;
    private final LookAroundService lookAroundService;

    @Transactional
    public Response<?> showLocation(Long userId) {
        Response<?> result;
        Player player = playerRepository.findById(userId).orElseThrow(NoRegisteredUserException::new);

        result = switch (player.getState()) {
            case WALK -> new StringResponse("Ты еще в дороге");
            case DEAD ->
                    new StringResponse("Приподнимаясь на койке, ты понимаешь, что находишься в столичном госпитале. Силы покидают тебя, и ты теряешь сознание");
            // TODO another dungeons?
            case DUNGEONS, DUNGEON_PVE, DUNGEON_WALK -> new StringResponse("Ты в подземелье, смотреть некуда");
            case PVE, PVP -> new StringResponse("Не отвлекайся, сначала закончи бой!");
            default -> null;
        };

        if (result == null) {
            Optional<InputStream> optionalBackgroundImage = getBackgroundImageService.getBackgroundForPlayer(player);
            ReplyKeyboard keyboard = getReplyKeyboardByState.getKeyboard(player);
            String lookAroungString = lookAroundService.getLookAroungString(player);

            if (optionalBackgroundImage.isPresent()) {
                result = new PhotoResponse(optionalBackgroundImage.get(), "image", lookAroungString, keyboard);
            } else {
                result = new StringWithKeyboardResponse(lookAroungString, keyboard);
            }
        }

        return result;
    }
}
