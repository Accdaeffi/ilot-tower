package ru.ilot.ilottower.logic.command.geo.lookaround;

import org.springframework.stereotype.Service;
import ru.ilot.ilottower.model.entities.geo.Location;
import ru.ilot.ilottower.model.entities.user.Player;
import ru.ilot.ilottower.model.enums.StateOfPlayer;

@Service
public class GetPlayersAroundService {

    public String getPlayerNearby(Player player, Location location) {
        var sb = new StringBuilder();
        int index = 0;

        for (Player anotherPlayer : location.getPlayers()) {
            if (index > 10) {
                sb.append("В толпе ещё кто-то есть, но не разглядеть...").append("\n");
                break;
            }

            // TODO change PK
            //PkState.ChangePkStateLow(u);
            if (anotherPlayer.getLevel() > player.getLevel() + 5 || anotherPlayer.getLevel() < player.getLevel() - 5) {
                //Nothing to do we can't see player
            } else {
                if (anotherPlayer.getId() != player.getId()) {
                    var show = (anotherPlayer.getState() == StateOfPlayer.IDLE &&
                            anotherPlayer.getLocation().getId().equals(location.getId()) &&
                            anotherPlayer.getBuildingLocation() == player.getBuildingLocation());
                    //anotherPlayer.HallId == player.HallId);

                    if (show) {
                        //sb.append(STR."\{EmojiGenerator.GetPkStatus(anotherPlayer)} \{anotherPlayer.UserName} \{cmd}");
                        sb.append(STR."\{anotherPlayer.getUsername()}").append("\n");
                        index++;
                    }
                }
            }
        }
        return sb.toString();
    }
}
