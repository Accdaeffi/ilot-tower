package ru.ilot.ilottower.model.repository.dungeon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ilot.ilottower.model.entities.dungeon.Dungeon;
import ru.ilot.ilottower.model.entities.dungeon.DungeonParty;

import java.util.List;
import java.util.Optional;

@Repository
public interface DungeonPartyRepository extends JpaRepository<DungeonParty, Integer> {
    List<DungeonParty> findAllByDungeon(Dungeon dungeon);
    Optional<DungeonParty> findFirstByLeaderId(long leaderId);
}
