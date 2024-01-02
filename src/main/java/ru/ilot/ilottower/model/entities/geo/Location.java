package ru.ilot.ilottower.model.entities.geo;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import ru.ilot.ilottower.enums.geo.TypeLocation;
import ru.ilot.ilottower.model.entities.items.Item;
import ru.ilot.ilottower.model.entities.user.Player;

import java.util.List;

@Data
@Entity
@Table(name = "location")
public class Location {
    @Id
    public String id;
    public int levelId;
    public int locationX;
    public int locationY;
    public TypeLocation locationType;
    public int monsterCount = 10;
    public int maxMonsterCount = 20;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
    public List<Building> BuildingList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
    public List<Player> players;
}
