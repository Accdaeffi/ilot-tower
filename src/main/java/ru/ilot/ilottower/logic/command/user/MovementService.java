package ru.ilot.ilottower.logic.command.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilot.ilottower.logic.async.user.WalkingAsyncService;
import ru.ilot.ilottower.model.entities.geo.Floor;
import ru.ilot.ilottower.model.entities.geo.Location;
import ru.ilot.ilottower.model.entities.user.Player;
import ru.ilot.ilottower.model.enums.StateOfPlayer;
import ru.ilot.ilottower.model.enums.geo.BuildingType;
import ru.ilot.ilottower.model.enums.geo.WalkingPath;
import ru.ilot.ilottower.model.repository.geo.FloorRepository;
import ru.ilot.ilottower.model.repository.geo.LocationRepository;
import ru.ilot.ilottower.model.repository.user.PlayerRepository;
import ru.ilot.ilottower.telegram.exception.DataLogicException;
import ru.ilot.ilottower.telegram.exception.NoRegisteredUserException;
import ru.ilot.ilottower.telegram.keyboard.MiniReplyKeyboardGetter;
import ru.ilot.ilottower.telegram.response.Response;
import ru.ilot.ilottower.telegram.response.StringResponse;
import ru.ilot.ilottower.telegram.response.StringWithKeyboardResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovementService implements ApplicationContextAware {

    private final PlayerRepository playerRepository;

    private final FloorRepository floorRepository;

    private final LocationRepository locationRepository;

    private final MiniReplyKeyboardGetter miniReplyKeyboardGetter;

    private ApplicationContext applicationContext;

    @Transactional
    public Response<String> move(Long userId, String commandText) {
        Response<String> result;

        Player user = playerRepository.findById(userId).orElseThrow(NoRegisteredUserException::new);

        WalkingPath walkingPath = switch (commandText) {
            case "⬆️ Север" -> WalkingPath.NORTH;
            case "⬅️ Запад" -> WalkingPath.WEST;
            case "➡️ Восток" -> WalkingPath.EAST;
            case "⬇️ Юг" -> WalkingPath.SOUTH;
            default -> throw new IllegalStateException(STR."Unknown movement text: \{commandText}");
        };

        var whenWalkingReplyKeyboard = miniReplyKeyboardGetter.getKeyboard();

        var walkTimeInMilliSeconds = 10000;

        // TODO вернуть перевес
//        if (user.Backpack.MaxCount < WeightSystem.WeightCalculator.CountWeightNow(user)) {
//            walkTimeInMilliSeconds = walkTimeInMilliSeconds * 45;
//        }

        if (user.getState() == StateOfPlayer.IDLE && user.getBuildingLocation() == BuildingType.NONE) {
            Location newLocation = doStep(user, walkingPath);
            if (newLocation == null) {
                result = new StringResponse("Край карты!");
            } else {
                user.setState(StateOfPlayer.WALK);
                playerRepository.save(user);

                //            TextMovementsType textType = walkingPath switch
//            {
//                WalkingPath.NORTH =>TextMovementsType.North,
//                    WalkingPath.SOUTH =>TextMovementsType.South,
//                    WalkingPath.WEST =>TextMovementsType.West,
//                    WalkingPath.EAST =>TextMovementsType.East
//            };
//            return TextFunctions.GetTextMovements(textType, user.Gender);
                String stepResult = "Путешествие начинается!";

                // TODO вернуть антиспам
                //AntiSpam.removeFromWorking(userId);
                result = new StringWithKeyboardResponse(stepResult, whenWalkingReplyKeyboard);

                WalkingAsyncService walkingAsyncService = applicationContext.getBean(WalkingAsyncService.class, userId, newLocation.getId(), walkTimeInMilliSeconds);
                Thread.startVirtualThread(walkingAsyncService);
            }
        } else {
            //String failedMove = TextGenerator.FailedMove(userId);
            String failedMove = "Ты не можешь сейчас идти";
            result = new StringResponse(failedMove);
        }

        return result;
    }

    private Location doStep(Player user, WalkingPath walkingPath) {
        Location location = user.getLocation();
        if (checkMove(location, walkingPath, user.getId())) {
            Location newLocation = findNewLocation(location, user, walkingPath);
            // TODO вернуть тексты ходьбы
//            TextMovementsType textType = walkingPath switch
//            {
//                WalkingPath.NORTH =>TextMovementsType.North,
//                    WalkingPath.SOUTH =>TextMovementsType.South,
//                    WalkingPath.WEST =>TextMovementsType.West,
//                    WalkingPath.EAST =>TextMovementsType.East
//            };
//            return TextFunctions.GetTextMovements(textType, user.Gender);
            return newLocation;
        } else {
            return null;
        }
    }

    /// <summary>
    /// контроль выхода на границу зоны
    /// </summary>
    /// <param name="user"></param>
    /// <param name="walkingPath"></param>
    /// <returns></returns>
    private boolean checkMove(Location location, WalkingPath walkingPath, Long userId) {

        Floor level = floorRepository.findById(location.getLevelId())
                .orElseThrow(() -> new DataLogicException(STR."User \{userId} on non-existing level \{location.getLevelId()}"));

        return switch (walkingPath) {
            case WalkingPath.NORTH -> (location.getLocationY() + 1) <= level.getMaximumY();
            case WalkingPath.SOUTH -> (location.getLocationY() - 1) >= level.getMinimumY();
            case WalkingPath.EAST -> (location.getLocationX() + 1) <= level.getMaximumX();
            case WalkingPath.WEST -> (location.getLocationX() - 1) >= level.getMinimumX();
        };
    }

    private Location findNewLocation(Location location, Player user, WalkingPath walkingPath) {
        int x;
        int y;

        switch (walkingPath) {
            case WalkingPath.EAST: {
                x = location.getLocationX() + 1;
                y = location.getLocationY();
            }
            break;
            case WalkingPath.WEST: {
                x = location.getLocationX() - 1;
                y = location.getLocationY();
            }
            break;
            case WalkingPath.NORTH: {
                x = location.getLocationX();
                y = location.getLocationY() + 1;
            }
            break;
            case WalkingPath.SOUTH: {
                x = location.getLocationX();
                y = location.getLocationY() - 1;
            }
            break;
            default: {
                x = location.getLocationX();
                y = location.getLocationY();
            }
        }

        return locationRepository.findFirstByLevelIdAndLocationXAndLocationY(location.getLevelId(), x, y)
                .orElseThrow(() -> new DataLogicException(STR."User \{user.getId()} moving to non-existing location \{location.getLevelId()}:\{x}:\{y}"));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
