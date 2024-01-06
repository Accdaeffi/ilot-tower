package ru.ilot.ilottower.model.entities.dungeon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import ru.ilot.ilottower.model.enums.dungeon.DungeonCellType;

@Data
@Entity
@Table(name = "dungeon_cell")
public class DungeonCell {
    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dungeon_id", nullable = false)
    private Dungeon dungeon;

    @Column(name = "x")
    private int x;

    @Column(name = "y")
    private int y;

    @Column(name = "cell_type")
    private DungeonCellType cellType;

    @Column(name = "inside")
    private int inside;

    @Column(name = "is_known")
    private boolean isKnown;
}
