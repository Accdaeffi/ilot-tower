package ru.ilot.ilottower.model.entities.user;

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
    public int id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "settings")
    public Player player;
    public boolean banned = false;
    public int expMultiply = 1;
}
