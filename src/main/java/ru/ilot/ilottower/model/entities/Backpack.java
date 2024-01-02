package ru.ilot.ilottower.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import ru.ilot.ilottower.model.entities.user.Player;

import java.util.List;

@Data
@Entity
@Table(name = "backpack")
public class Backpack {

    @Id
    public int Id;
    public int MaxCount = 30;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "backpack")
    public List<BackpackItem> BackpackItems;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "backpack")
    public Player player;
}
