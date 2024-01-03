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
@Table(name = "user_settings")
public class UserSettings {

    @Id
    @Column(name = "Id")
    private int id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "settings")
    private Player player;

    @Column(name = "Banned")
    private boolean banned = false;

    @Column(name = "ExpMultiply")
    private int expMultiply = 1;
}
