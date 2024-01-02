package ru.ilot.ilottower.model.entities.money;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "currency")
public class Currency {
    @Id
    public int id;
    public String name;
    public boolean isBase;
    public BigDecimal exchangeRate;
    public String nameShort;
}
