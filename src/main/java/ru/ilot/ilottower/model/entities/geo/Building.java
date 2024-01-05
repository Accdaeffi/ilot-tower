package ru.ilot.ilottower.model.entities.geo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import ru.ilot.ilottower.model.enums.geo.BuildingType;

@Data
@Entity
@Table(name = "building")
public class Building {
    @Id
    @Column(name = "id")
    public int id;

    @Column(name = "name")
    public String name;

    @Column(name = "building_type")
    @Enumerated(EnumType.ORDINAL)
    public BuildingType buildingType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="location_id", nullable=false)
    public Location location;
}
