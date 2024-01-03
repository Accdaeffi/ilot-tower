package ru.ilot.ilottower.model.entities.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "stats_player")
public class StatsPlayer {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "hp_current")
    private int hpCurrent = 150;

    @Column(name = "hp_total")
    private int hpTotal = 150;

    @Column(name = "attack_total")
    private int attackTotal = 10;

    @Column(name = "attack_base")
    private int attackBase = 10;

    @Column(name = "defence_total")
    private int defenceTotal = 10;

    @Column(name = "defence_base")
    private int defenceBase = 10;

    @Column(name = "strength_total")
    private int strengthTotal = 0;

    @Column(name = "dexterity_total")
    private int dexterityTotal = 0;

    @Column(name = "critical_total")
    private int criticalTotal = 3;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Player player;
}
