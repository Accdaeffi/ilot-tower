package ru.ilot.ilottower.telegram.keyboard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.ilot.ilottower.model.entities.user.Player;
import ru.ilot.ilottower.model.enums.StateOfPlayer;
import ru.ilot.ilottower.model.enums.geo.BuildingType;

@Service
@RequiredArgsConstructor
public class GetReplyKeyboardByState {

    private final IdleReplyKeyboardGetter idleReplyKeyboardGetter;
    private final WalkingReplyKeyboardGetter walkingReplyKeyboardGetter;

    // TODO вернуть другие
    public ReplyKeyboard getKeyboard(Player user)
    {
        ReplyKeyboard res = null;

        switch (user.getState())
        {
            case StateOfPlayer.DEAD:
                //res = DeadReplyKeyboard();
                break;
            case StateOfPlayer.WALK:
                res = walkingReplyKeyboardGetter.getKeyboard();
                break;
            case StateOfPlayer.DUNGEONS:
            case StateOfPlayer.DUNGEON_PVE:
            case StateOfPlayer.DUNGEON_LOOT:
            case StateOfPlayer.DUNGEON_WALK:
                //res = DungeonInlineKeyboard(user);
                break;
            case StateOfPlayer.DUNGEON_BOSS:
                //res = DungeonKeyboard.GetKeyboardRaidBoss();
                break;
            default:
                switch (user.getBuildingLocation())
                {
                    case BuildingType.SAWMILL:
                    case BuildingType.ALCHEMICAL_GLADE:
                    case BuildingType.FISHING:
                    case BuildingType.MINING:
                        //res = KeyboardsByBuildingType.InsideKeyboard(user.BuildingLocation);
                        break;
                    case BuildingType.ARENA:
                        //res = KeyboardsByBuildingType.ArenaKeyboard(user);
                        break;
                    case BuildingType.DUNGEON:
                        //res = DungeonInlineKeyboard(user);
                        break;
                    default:
                        res = idleReplyKeyboardGetter.getKeyboard();
                        break;
                }
                break;
        }
        return res;
    }
}
