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
import ru.ilot.ilottower.model.enums.PkStatus;
import ru.ilot.ilottower.model.enums.PlayerGender;
import ru.ilot.ilottower.model.enums.StateOfPlayer;
import ru.ilot.ilottower.model.enums.geo.BuildingType;
import ru.ilot.ilottower.model.entities.Backpack;
import ru.ilot.ilottower.model.entities.geo.Location;
import ru.ilot.ilottower.model.entities.money.Wallet;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "player")
public class Player {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "level")
    private int level = 1;

    @Column(name = "exp_current")
    private int expCurrent = 0;

//    @Column(name = "location_level")
//    private int locationLevel = 1;
//
//    @Column(name = "location_x")
//    private int locationX = 0;
//
//    @Column(name = "location_y")
//    private int locationY = 0;

    @Column(name = "gender")
    @Enumerated(EnumType.ORDINAL)
    private PlayerGender gender;

    @Column(name = "state")
    @Enumerated(EnumType.ORDINAL)
    private StateOfPlayer state = StateOfPlayer.IDLE;

    @Column(name = "pk_status")
    @Enumerated(EnumType.ORDINAL)
    private PkStatus pkStatus = PkStatus.GOOD;

    @Column(name = "timestamp_pk_status_end")
    private Timestamp timestampPkStatusEnd;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "player")
    private StatsPlayer stats;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "player")
    private Backpack backpack;

    // TODO wallets
//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "player")
//    private Wallet wallet;

    // TODO add skills
//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "player")
//    public SkillPlayer SkillPlayer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="location_id", nullable=false)
    private Location location;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "player")
    private UserSettings settings;

    // TODO quests system
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "player")
//    public List<QuestsState> questState;

    // TODO вернуть статистику
//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "player")
//    public StatisticFields statistic;

    @Column(name = "walk_speed")
    private int walkSpeed;

    // TODO save building
    @Column(name = "building_location")
    @Enumerated(EnumType.ORDINAL)
    private BuildingType buildingLocation;

    // TODO building refactor
//    public int hallId;
//    @ManyToOne(fetch = FetchType.LAZY)
//    public Hall hall;

}
