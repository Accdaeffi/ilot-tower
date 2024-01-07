package ru.ilot.ilottower.model.repository.dungeon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ilot.ilottower.model.entities.dungeon.DungeonPartyPlayer;

import java.util.Optional;

@Repository
public interface DungeonPartyPlayerRepository extends JpaRepository<DungeonPartyPlayer, Long> {
    Optional<DungeonPartyPlayer> findFirstByPlayerId(long playerId);
}
