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
import ru.ilot.ilottower.enums.geo.BuildingType;

@Data
@Entity
@Table(name = "Buildings")
public class Building {
    @Id
    @Column(name = "Id")
    public int id;

    @Column(name = "Name")
    public String name;

    @Column(name = "BuildingType")
    @Enumerated(EnumType.ORDINAL)
    public BuildingType buildingType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="LocationId", nullable=false)
    public Location location;
}
