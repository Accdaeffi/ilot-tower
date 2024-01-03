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
@Table(name = "user_settings")
public class UserSettings {

    @Id
    @Column(name = "id")
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Player player;

    @Column(name = "is_banned")
    private boolean isBanned = false;

    @Column(name = "exp_multiply")
    private int expMultiply = 1;
}
