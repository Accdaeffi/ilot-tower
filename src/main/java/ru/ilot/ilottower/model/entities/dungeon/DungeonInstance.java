package ru.ilot.ilottower.model.entities.dungeon;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "dungeon_instance")
public class DungeonInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "height")
    private int height;

    @Column(name = "width")
    private int width;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    private DungeonParty party;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dungeon", cascade = CascadeType.ALL)
    private List<DungeonCell> rooms;
}
