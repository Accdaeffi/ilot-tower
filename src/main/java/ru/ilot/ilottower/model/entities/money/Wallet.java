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
import ru.ilot.ilottower.model.enums.MoneyVector;
import ru.ilot.ilottower.model.enums.PaymentReason;
import ru.ilot.ilottower.model.entities.user.Player;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    @Column(name = "id")
    public int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", nullable = false)
    public Currency currency;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public Player player;

    @Column(name = "customer_id")
    public int customerId;

    @Column(name = "date")
    public Timestamp date;

    @Column(name = "amount")
    public int amount;

    @Column(name = "vector")
    public MoneyVector vector;

    @Column(name = "reason_id")
    public PaymentReason reasonId;

    //public int tradingId;
    //public virtual Trading Trading;

    @Column(name = "is_donat")
    public boolean isDonat;
}
