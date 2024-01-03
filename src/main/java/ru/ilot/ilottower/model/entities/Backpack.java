package ru.ilot.ilottower.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    @Column(name = "id")
    private int id;

    @Column(name = "max_count")
    private int maxCount = 30;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "backpack")
    private List<BackpackItem> BackpackItems;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Player player;
}
