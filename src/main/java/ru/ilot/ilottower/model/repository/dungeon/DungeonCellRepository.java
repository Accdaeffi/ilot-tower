package ru.ilot.ilottower.model.repository.dungeon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ilot.ilottower.model.entities.dungeon.DungeonCell;

@Repository
public interface DungeonCellRepository extends JpaRepository<DungeonCell, Long> {
}
