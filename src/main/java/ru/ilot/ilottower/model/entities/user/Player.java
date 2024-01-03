package ru.ilot.ilottower.model.entities.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "Players")
public class Player {

    @Id
    @Column(name = "Id")
    public int id;

    @Column(name = "UserName")
    public String username;

    @Column(name = "Level")
    public int level = 1;

    @Column(name = "ExpCurrent")
    public int expCurrent = 0;

    @Column(name = "LocationLevel")
    public int locationLevel = 1;

    @Column(name = "LocationX")
    public int locationX = 0;

    @Column(name = "LocationY")
    public int locationY = 0;

    @Column(name = "LocationId")
    public String locationId;

    @Column(name = "Gender")
    @Enumerated(EnumType.ORDINAL)
    public PlayerGender gender;

    @Column(name = "state")
    @Enumerated(EnumType.ORDINAL)
    public StateOfPlayer state = StateOfPlayer.IDLE;

    @Column(name = "PkStatus")
    @Enumerated(EnumType.ORDINAL)
    public PkStatus pkStatus = PkStatus.GOOD;

    @Column(name = "TimestampPkStatusEnd")
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
    @JoinColumn(name="LocationId", nullable=false)
    public Location location;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "player")
    public UserSettings settings;

    // TODO quests system
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "player")
//    public List<QuestsState> questState;

    // TODO вернуть статистику
//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "player")
//    public StatisticFields statistic;

    @Column(name = "WalkSpeed")
    public int walkSpeed;

    @Column(name = "BuildingLocation")
    @Enumerated(EnumType.ORDINAL)
    public BuildingType buildingLocation;

    // TODO building refactor
//    public int hallId;
//    @ManyToOne(fetch = FetchType.LAZY)
//    public Hall hall;

}
