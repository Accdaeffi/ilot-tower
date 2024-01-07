package ru.ilot.ilottower.logic.command.dungeon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ilot.ilottower.model.entities.dungeon.DungeonParty;
import ru.ilot.ilottower.model.repository.dungeon.DungeonPartyRepository;
import ru.ilot.ilottower.telegram.response.StringResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MakePartyInviteOnlyService {
    private final DungeonPartyRepository dungeonPartyRepository;

    public StringResponse makePartyInviteOnly(long userId, boolean isInviteOnly) {
        Optional<DungeonParty> optionalDungeonParty = dungeonPartyRepository.findFirstByLeaderId(userId);

        if (optionalDungeonParty.isEmpty())
        {
            return new StringResponse("А ты точно лидер команды?");
        }
        DungeonParty dungeonParty = optionalDungeonParty.get();

        if (dungeonParty.isEntered())
        {
            return new StringResponse("Поздно менять настройки приглашения!");
        }

        dungeonParty.setInviteOnly(isInviteOnly);
        dungeonPartyRepository.save(dungeonParty);

        if (isInviteOnly)
        {
            return new StringResponse("""
                    Теперь в твою команду может вступить только имеющий приглашение!
                    Чтобы послать приглашение, используй команду /inviteParty_{id игрока}!""");
        } else
        {
            return new StringResponse("Теперь в твою команду может вступить любой!");
        }
    }

}
