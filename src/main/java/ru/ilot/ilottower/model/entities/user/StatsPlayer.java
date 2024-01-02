package ru.ilot.ilottower.model.entities.user;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "stats_player")
public class StatsPlayer {
    @Id
    public int Id;
    public int HpCurrent = 150;
    public int HpTotal = 150;
    public int AttackTotal = 10;
    public int AttackBase = 10;
    public int DefenceTotal = 10;
    public int DefenceBase = 10;
    public int StrengthTotal = 0;
    public int DexterityTotal = 0;
    public int CriticalTotal = 3;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "stats")
    public Player player;
}
