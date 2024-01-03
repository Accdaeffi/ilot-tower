package ru.ilot.ilottower.model.entities.money;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import ru.ilot.ilottower.enums.MoneyVector;
import ru.ilot.ilottower.enums.PaymentReason;
import ru.ilot.ilottower.model.entities.user.Player;

import java.sql.Timestamp;
import java.util.Currency;

@Data
@Entity
@Table(name = "Wallets")
public class Wallet {

    @Id
    @Column(name = "Id")
    public int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CurrencyId", nullable = false)
    public Currency currency;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "wallet")
    @JoinColumn(name = "UserId")
    public Player player;

    @Column(name = "CustomerId")
    public int customerId;

    @Column(name = "Date")
    public Timestamp date;

    @Column(name = "Amount")
    public int amount;

    @Column(name = "Vector")
    public MoneyVector vector;

    @Column(name = "ReasonId")
    public PaymentReason reasonId;

    //public int tradingId;
    //public virtual Trading Trading;

    @Column(name = "Donat")
    public boolean isDonat;
}
