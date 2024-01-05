package ru.ilot.ilottower.model.entities.geo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "floor")
public class Floor {
    @Id
    @Column(name = "id")
    public int id;
    @Column(name = "maximum_x")
    public int maximumX;
    @Column(name = "minimum_x")
    public int minimumX;
    @Column(name = "maximum_y")
    public int maximumY;
    @Column(name = "minimum_y")
    public int minimumY;
    @Column(name = "is_opened")
    public boolean isOpened;
}
