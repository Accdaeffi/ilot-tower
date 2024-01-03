package ru.ilot.ilottower.model.entities.money;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "Currencies")
public class Currency {
    @Id
    @Column(name = "Id")
    public int id;

    @Column(name = "Name")
    public String name;

    @Column(name = "IsBase")
    public boolean isBase;

    @Column(name = "ExchangeRate")
    public BigDecimal exchangeRate;

    @Column(name = "NameShort")
    public String nameShort;
}
