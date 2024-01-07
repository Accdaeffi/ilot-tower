package ru.ilot.ilottower.model.repository.dungeon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ilot.ilottower.model.entities.dungeon.Dungeon;

import java.util.Optional;

@Repository
public interface DungeonRepository extends JpaRepository<Dungeon, Integer> {
    Optional<Dungeon> findFirstByLocationId(String locationId);
}
