//package ru.ilot.ilottower.model.entities.user;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.Id;
//import jakarta.persistence.OneToOne;
//import jakarta.persistence.Table;
//import lombok.Data;
//
//import java.sql.Timestamp;
//
//@Data
//@Entity
//@Table(name = "statistic_fields")
//public class StatisticFields {
//    @Id
//    public int id;
//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "statistic")
//    public Player player;
//    public Timestamp startDate = new Timestamp(System.currentTimeMillis());
//    public int steps = 0;
//    public int killMonster = 0;
//    public int death = 0;
//    public int winPvp = 0;
//    public int losePvp = 0;
//    public int invitedFriends = 0;
//    public int bossWin = 0;
//    public int arenaWin = 0;
//    public int questFinished = 0;
//    public int itemCrafted = 0;
//}
