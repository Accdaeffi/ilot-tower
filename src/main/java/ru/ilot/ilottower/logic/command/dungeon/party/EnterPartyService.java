package ru.ilot.ilottower.logic.command.dungeon.party;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilot.ilottower.logic.command.dungeon.DungeonUtil;
import ru.ilot.ilottower.model.entities.dungeon.Dungeon;
import ru.ilot.ilottower.model.entities.dungeon.DungeonParty;
import ru.ilot.ilottower.model.entities.dungeon.DungeonPartyInvitation;
import ru.ilot.ilottower.model.entities.dungeon.DungeonPartyPlayer;
import ru.ilot.ilottower.model.entities.user.Player;
import ru.ilot.ilottower.model.enums.StateOfPlayer;
import ru.ilot.ilottower.model.repository.dungeon.DungeonPartyPlayerRepository;
import ru.ilot.ilottower.model.repository.dungeon.DungeonPartyRepository;
import ru.ilot.ilottower.model.repository.dungeon.DungeonRepository;
import ru.ilot.ilottower.model.repository.user.PlayerRepository;
import ru.ilot.ilottower.telegram.exception.NoRegisteredUserException;
import ru.ilot.ilottower.telegram.response.Response;
import ru.ilot.ilottower.telegram.response.StringResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnterPartyService {
    private final PlayerRepository playerRepository;
    private final DungeonRepository dungeonRepository;
    private final DungeonPartyRepository dungeonPartyRepository;
    private final DungeonPartyPlayerRepository dungeonPartyPlayerRepository;
    private final DungeonUtil dungeonUtil;

    @Transactional
    public Response<?> enterParty(long playerId, int partyId) {
        Player player = playerRepository.findById(playerId).orElseThrow(NoRegisteredUserException::new);
        if (player.getState() != StateOfPlayer.IDLE) {
            return new StringResponse("Ты чем-то занят!");
        }

        Optional<DungeonParty> optionalParty = dungeonPartyRepository.findById(partyId);
        if (optionalParty.isEmpty()) {
            return new StringResponse("Такая команда не найдена!");
        }
        DungeonParty party = optionalParty.get();

        Optional<Dungeon> optionalDungeon = dungeonRepository.findFirstByLocationId(player.getLocation().getId());
        if (optionalDungeon.isEmpty()) {
            return new StringResponse("Ты не у входа в данж!");
        }
        Dungeon dungeon = optionalDungeon.get();

        if (!dungeon.equals(party.getDungeon())) {
            return new StringResponse("Это команда готовится к походу в другой данж!");
        }

        if (party.getPlayers().size() >= 5) {
            return new StringResponse("Команда уже заполнена!");
        }

        if (party.isEntered()) {
            return new StringResponse("Это команда уже затерялась в данже!");
        }

        Optional<DungeonPartyInvitation> dungeonPartyInvitation = party.getInviteList().stream().filter(invitation -> invitation.getPlayer().equals(player)).findFirst();
        if (party.isInviteOnly() && dungeonPartyInvitation.isEmpty()) {
            return new StringResponse("Вы кто такие? Я вас не звал!");
        }

        Optional<DungeonPartyPlayer> optionalDungeonPartyPlayer = dungeonPartyPlayerRepository.findFirstByPlayerId(playerId);
        if (optionalDungeonPartyPlayer.isPresent()) {
            return new StringResponse("Ты в своей команде сиди, а не на другие заглядывайся!");
        }

        DungeonPartyPlayer dungeonPartyPlayer = new DungeonPartyPlayer();
        dungeonPartyPlayer.setParty(party);
        dungeonPartyPlayer.setPlayer(player);

        party.getPlayers().add(dungeonPartyPlayer);

        dungeonPartyInvitation.ifPresent(invitation -> party.getInviteList().remove(invitation));

        dungeonPartyRepository.save(party);

        StringResponse enterMessage = new StringResponse(STR."\{player.getUsername()} вступил в команду!");
        dungeonUtil.broadcastMessage(party, enterMessage);

        return new StringResponse("Добро пожаловать в команду!");
    }
}
