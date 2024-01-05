package ru.ilot.ilottower.model.entities.geo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import ru.ilot.ilottower.model.enums.geo.LocationType;
import ru.ilot.ilottower.model.entities.user.Player;

import java.util.List;

@Data
@Entity
@Table(name = "location")
public class Location {
    @Id
    @Column(name = "id")
    public String id;

    @Column(name = "level_id")
    public int levelId;

    @Column(name = "location_x")
    public int locationX;

    @Column(name = "location_y")
    public int locationY;

    @Column(name = "location_type")
    public LocationType locationType;

    @Column(name = "monster_count")
    public int monsterCount = 10;

    @Column(name = "max_monster_count")
    public int maxMonsterCount = 20;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
    public List<Building> BuildingList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
    public List<Player> players;
}
