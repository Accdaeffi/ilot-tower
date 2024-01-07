package ru.ilot.ilottower.logic.command.dungeon.party;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilot.ilottower.model.entities.dungeon.DungeonParty;
import ru.ilot.ilottower.model.entities.dungeon.DungeonPartyInvitation;
import ru.ilot.ilottower.model.entities.dungeon.DungeonPartyPlayer;
import ru.ilot.ilottower.model.entities.geo.Location;
import ru.ilot.ilottower.model.entities.user.Player;
import ru.ilot.ilottower.model.entities.user.StatsPlayer;
import ru.ilot.ilottower.model.repository.dungeon.DungeonPartyPlayerRepository;
import ru.ilot.ilottower.telegram.response.StringResponse;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViewMyPartyService {
    private final DungeonPartyPlayerRepository dungeonPartyPlayerRepository;

    @Transactional
    public StringResponse viewMyParty(long playerId) {

        Optional<DungeonPartyPlayer> optionalDungeonPartyPlayer = dungeonPartyPlayerRepository.findFirstByPlayerId(playerId);
        if (optionalDungeonPartyPlayer.isEmpty())
        {
            return new StringResponse("Так ты ж не в команде!");
        }
        DungeonParty dungeonParty = optionalDungeonPartyPlayer.get().getParty();

        List<Player> participants = dungeonParty.getPlayers().stream().map(DungeonPartyPlayer::getPlayer).toList();
        List<Player> invited = dungeonParty.getInviteList().stream().map(DungeonPartyInvitation::getPlayer).toList();

        StringBuilder sb = new StringBuilder();
        sb.append(STR."<b>👥 Команда №\{dungeonParty.getId()}</b>").append("\n");
        sb.append("\n");
        sb.append("<i>Участники:</i>").append("\n");
        for (Player player : participants)
        {
            sb.append(STR."<b>\{(player.equals(dungeonParty.getLeader()) ? "👑" : "")}\{player.getUsername()}</b> 🎖\{player.getLevel()}").append("\n");
            StatsPlayer statsPlayer = player.getStats();
            sb.append(STR."💚\{statsPlayer.getHpCurrent()}/\{statsPlayer.getHpTotal()} 🗡\{statsPlayer.getAttackTotal()} 🛡\{player.getStats().getAttackTotal()}").append("\n");
            Location location = player.getLocation();
            sb.append(STR."\{location.getLevelId()} Этаж (↔️\{location.getLocationX()}:↕️\{location.getLocationY()})").append("\n");
            sb.append("\n");
        }

        if (!invited.isEmpty())
        {
            sb.append("<i>Приглашены:</i>").append("\n");
            for (Player player : invited)
            {
                sb.append(STR."<b>\{player.getUsername()}</b> 🎖\{player.getLevel()}").append("\n");
                Location location = player.getLocation();
                sb.append(STR."\{location.getLevelId()} Этаж (↔️\{location.getLocationX()}:↕️\{location.getLocationY()})").append("\n");
                sb.append("\n");
            }
        }

        String isInviteOnly = !dungeonParty.isInviteOnly() ? "Можно" : "Нельзя";
        sb.append(STR."\{isInviteOnly} вступить без приглашения").append("\n");

        return new StringResponse(sb.toString());
    }
}
