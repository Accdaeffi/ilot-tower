package ru.ilot.ilottower.model.entities.geo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import ru.ilot.ilottower.enums.geo.TypeLocation;
import ru.ilot.ilottower.model.entities.user.Player;

import java.util.List;

@Data
@Entity
@Table(name = "Locations")
public class Location {
    @Id
    @Column(name = "Id")
    public String id;

    @Column(name = "LevelId")
    public int levelId;

    @Column(name = "LocationX")
    public int locationX;

    @Column(name = "LocationY")
    public int locationY;

    @Column(name = "LocationType")
    public TypeLocation locationType;

    @Column(name = "MonsterCount")
    public int monsterCount = 10;

    @Column(name = "MaxMonsterCount")
    public int maxMonsterCount = 20;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
    public List<Building> BuildingList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
    public List<Player> players;
}
