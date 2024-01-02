package ru.ilot.ilottower.model.entities.geo;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import ru.ilot.ilottower.enums.geo.BuildingType;

@Data
@Entity
@Table(name = "building")
public class Building {
    @Id
    public int id;
    public String name;
    public BuildingType buildingType;

    @ManyToOne(fetch = FetchType.LAZY)
    public Location location;
}
