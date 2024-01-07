package ru.ilot.ilottower.model.entities.dungeon;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import ru.ilot.ilottower.model.entities.user.Player;

import java.util.List;

@Data
@Entity
@Table(name = "dungeon_party")
public class DungeonParty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dungeon_id")
    private Dungeon dungeon;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id")
    private Player leader;

    @OneToMany(mappedBy = "party", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<DungeonPartyPlayer> players;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "party", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<DungeonPartyInvitation> inviteList;

    @Column(name = "is_entered")
    private boolean isEntered;

    @Column(name = "is_invite_only")
    private boolean isInviteOnly;
}
