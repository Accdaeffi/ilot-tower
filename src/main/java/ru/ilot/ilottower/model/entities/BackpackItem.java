package ru.ilot.ilottower.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import ru.ilot.ilottower.model.entities.items.Item;

@Data
@Entity
@Table(name = "backpack_item")
public class BackpackItem {
    @Id
    public int id;
    public int count;
    public boolean isEquiped = false;

    @ManyToOne(fetch = FetchType.LAZY)
    public Backpack backpack;

    @ManyToOne(fetch = FetchType.LAZY)
    public Item item;
}
