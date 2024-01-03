package ru.ilot.ilottower.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import ru.ilot.ilottower.model.entities.items.Item;

@Data
@Entity
@Table(name = "backpack_item")
public class BackpackItem {
    @Id
    @Column(name = "id")
    public int id;

    @Column(name = "count")
    public int count;

    @Column(name = "is_equipped")
    public boolean isEquipped = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "backpack_id", nullable = false)
    public Backpack backpack;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    public Item item;
}
