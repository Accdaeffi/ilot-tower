package ru.ilot.ilottower.model.entities.dungeon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import ru.ilot.ilottower.model.entities.geo.Building;

import java.util.List;

// TODO building refactoring - problems of ORM
@Data
@Entity
@Table(name = "dungeon")
@PrimaryKeyJoinColumn(name = "building_id")
public class Dungeon extends Building {

    @Column(name = "height")
    private int height;

    @Column(name = "width")
    private int width;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dungeon")
    private List<DungeonCell> rooms;
}
