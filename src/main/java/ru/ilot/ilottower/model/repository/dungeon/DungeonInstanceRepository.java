package ru.ilot.ilottower.model.repository.dungeon;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ilot.ilottower.model.entities.dungeon.DungeonInstance;

public interface DungeonInstanceRepository extends JpaRepository<DungeonInstance, Long> {
}
