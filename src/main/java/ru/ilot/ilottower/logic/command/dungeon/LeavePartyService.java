package ru.ilot.ilottower.logic.command.dungeon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilot.ilottower.model.entities.dungeon.DungeonParty;
import ru.ilot.ilottower.model.entities.dungeon.DungeonPartyPlayer;
import ru.ilot.ilottower.model.entities.user.Player;
import ru.ilot.ilottower.model.repository.dungeon.DungeonPartyPlayerRepository;
import ru.ilot.ilottower.model.repository.dungeon.DungeonPartyRepository;
import ru.ilot.ilottower.model.repository.user.PlayerRepository;
import ru.ilot.ilottower.telegram.exception.DataLogicException;
import ru.ilot.ilottower.telegram.exception.NoRegisteredUserException;
import ru.ilot.ilottower.telegram.response.StringResponse;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeavePartyService {
    private final PlayerRepository playerRepository;
    private final DungeonPartyPlayerRepository dungeonPartyPlayerRepository;
    private final DungeonPartyRepository dungeonPartyRepository;

    private final DungeonUtil dungeonUtil;

    @Transactional
    public StringResponse leaveParty(long playerId)
    {
        Player player = playerRepository.findById(playerId).orElseThrow(NoRegisteredUserException::new);

        Optional<DungeonPartyPlayer> optionalDungeonPartyPlayer = dungeonPartyPlayerRepository.findFirstByPlayerId(playerId);
        if (optionalDungeonPartyPlayer.isEmpty())
        {
            return new StringResponse("И откуда ты ливать собрался?");
        }
        DungeonPartyPlayer dungeonPartyPlayer = optionalDungeonPartyPlayer.get();

        DungeonParty dungeonParty = dungeonPartyPlayer.getParty();
        if (dungeonParty.isEntered())
        {
            return new StringResponse("Из данжа есть только 2 выхода - могила или выход.");
        }
        List<DungeonPartyPlayer> partyPlayers = dungeonParty.getPlayers();

        if (partyPlayers.size() == 1) {
            dungeonPartyRepository.delete(dungeonParty);
        } else {
            partyPlayers.remove(dungeonPartyPlayer);
            dungeonPartyPlayerRepository.delete(dungeonPartyPlayer);
            if (dungeonParty.getLeader().equals(player))
            {
                dungeonParty.setLeader(partyPlayers.getFirst().getPlayer());
            }
            dungeonParty = dungeonPartyRepository.save(dungeonParty);

            StringResponse messageToBroadcast = new StringResponse(STR."\{player.getUsername()} покинул команду!");
            dungeonUtil.broadcastMessage(dungeonParty, messageToBroadcast);
        }

        return new StringResponse("Поздравляю, теперь ты снова одинок!");
    }
}
