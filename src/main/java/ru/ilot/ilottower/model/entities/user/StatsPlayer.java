package ru.ilot.ilottower.model.entities.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "StatsPlayer")
public class StatsPlayer {

    @Id
    @Column(name = "Id")
    private int id;

    @Column(name = "HpCurrent")
    private int hpCurrent = 150;

    @Column(name = "HpTotal")
    private int hpTotal = 150;

    @Column(name = "AttackTotal")
    private int attackTotal = 10;

    @Column(name = "AttackBase")
    private int attackBase = 10;

    @Column(name = "DefenceTotal")
    private int defenceTotal = 10;

    @Column(name = "DefenceBase")
    private int defenceBase = 10;

    @Column(name = "StrengthTotal")
    private int strengthTotal = 0;

    @Column(name = "DexterityTotal")
    private int dexterityTotal = 0;

    @Column(name = "CriticalTotal")
    private int criticalTotal = 3;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "stats")
    private Player player;
}
