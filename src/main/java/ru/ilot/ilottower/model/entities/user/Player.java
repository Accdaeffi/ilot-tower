package ru.ilot.ilottower.model.entities.user;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import ru.ilot.ilottower.enums.PkStatus;
import ru.ilot.ilottower.enums.PlayerGender;
import ru.ilot.ilottower.enums.StateOfPlayer;
import ru.ilot.ilottower.enums.geo.BuildingType;
import ru.ilot.ilottower.model.entities.Backpack;
import ru.ilot.ilottower.model.entities.geo.Location;
import ru.ilot.ilottower.model.entities.money.Wallet;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "player")
public class Player {

    @Id
    public int id;

    public String username;
    public int level = 1;
    public int expCurrent = 0;
    public int locationLevel = 1;
    public int locationX = 0;
    public int locationY = 0;
    public String locationId;
    public PlayerGender gender;
    public StateOfPlayer state = StateOfPlayer.IDLE;
    public PkStatus pkStatus = PkStatus.GOOD;
    public Timestamp timestampPkStatusEnd;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "player")
    public StatsPlayer stats;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "player")
    public Backpack backpack;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "player")
    public Wallet wallet;

    // TODO add skills
//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "player")
//    public SkillPlayer SkillPlayer;

    @ManyToOne(fetch = FetchType.LAZY)
    public Location location;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "player")
    public UserSettings settings;

    // TODO quests system
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "player")
//    public List<QuestsState> questState;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "player")
    public StatisticFields statistic;

    public int walkSpeed;
    public BuildingType buildingLocation;

    // TODO building refactor
//    public int hallId;
//    @ManyToOne(fetch = FetchType.LAZY)
//    public Hall hall;

}
