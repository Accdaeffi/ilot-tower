package ru.ilot.ilottower.model.entities.dungeon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dungeon_id")
    private Dungeon dungeon;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id")
    private Player leader;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "party")
    private List<DungeonPartyPlayer> players;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "party")
    private List<DungeonPartyInvitation> inviteList;

    @Column(name = "is_entered")
    private boolean isEntered;

    @Column(name = "is_invite_only")
    private boolean isInviteOnly;
}
