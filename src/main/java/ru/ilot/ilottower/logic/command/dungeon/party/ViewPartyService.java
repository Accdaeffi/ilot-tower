package ru.ilot.ilottower.logic.command.dungeon.party;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilot.ilottower.model.entities.dungeon.Dungeon;
import ru.ilot.ilottower.model.entities.dungeon.DungeonParty;
import ru.ilot.ilottower.model.entities.dungeon.DungeonPartyPlayer;
import ru.ilot.ilottower.model.entities.geo.Building;
import ru.ilot.ilottower.model.entities.user.Player;
import ru.ilot.ilottower.model.enums.StateOfPlayer;
import ru.ilot.ilottower.model.repository.dungeon.DungeonPartyPlayerRepository;
import ru.ilot.ilottower.model.repository.dungeon.DungeonPartyRepository;
import ru.ilot.ilottower.model.repository.dungeon.DungeonRepository;
import ru.ilot.ilottower.model.repository.user.PlayerRepository;
import ru.ilot.ilottower.telegram.exception.NoRegisteredUserException;
import ru.ilot.ilottower.telegram.response.StringResponse;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViewPartyService {
    private final PlayerRepository playerRepository;
    private final DungeonRepository dungeonRepository;
    private final DungeonPartyPlayerRepository dungeonPartyPlayerRepository;
    private final DungeonPartyRepository dungeonPartyRepository;

    @Transactional
    public StringResponse viewParties(long playerId)
    {
        Player player = playerRepository.findById(playerId).orElseThrow(NoRegisteredUserException::new);

        if (player.getState() != StateOfPlayer.IDLE)
        {
            return new StringResponse("Сначала доделай текущее дело!");
        }

        Optional<Dungeon> optionalDungeon = dungeonRepository.findFirstByLocationId(player.getLocation().getId());
        if (optionalDungeon.isEmpty())
        {
            return new StringResponse("Нельзя смотреть команды там, где нет данжа!");
        }
        Dungeon dungeon = optionalDungeon.get();

        StringBuilder sb = new StringBuilder();

        Optional<DungeonPartyPlayer> optionalDungeonPartyPlayer = dungeonPartyPlayerRepository.findFirstByPlayerId(playerId);
        if (optionalDungeonPartyPlayer.isPresent())
        {
            return new StringResponse("Ты в своей команде сиди, а не на другие заглядывайся!");
        }

        List<DungeonParty> parties = dungeonPartyRepository.findAllByDungeon(dungeon);

        if (!parties.isEmpty()) {
            sb.append("<b>Список команд:</b>").append("\n");
            for (DungeonParty party : parties) {
                Player leader = party.getLeader();
                int count = party.getPlayers().size();
                String ending = "ами";
                if (count % 10 == 1) {
                    ending = "ом";
                }

                sb.append(STR."Команда с лидером \{leader.getUsername()} и \{count} участник\{ending}: /enterParty_\{party.getId()}").append("\n");
            }
        } else {
            sb.append("Рядом нет команд!");
        }
        return new StringResponse(sb.toString());
    }
}
