package ru.ilot.ilottower.model.entities.dungeon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "dungeon")
public class Dungeon {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "building_id")
    private int buildingId;

    @Column(name = "height")
    private int height;

    @Column(name = "width")
    private int width;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dungeon")
    private List<DungeonCell> rooms;
}
