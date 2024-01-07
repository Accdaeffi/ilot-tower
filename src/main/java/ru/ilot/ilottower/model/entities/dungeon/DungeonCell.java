package ru.ilot.ilottower.model.entities.dungeon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ilot.ilottower.model.enums.dungeon.DungeonCellType;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "dungeon_cell")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DungeonCell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dungeon_id", nullable = false)
    private DungeonInstance dungeon;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dungeonCell")
    private List<DungeonPartyPlayer> dungeonPartyPlayers = new ArrayList<>();

}
