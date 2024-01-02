package ru.ilot.ilottower.model.entities.money;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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
@Table(name = "wallet")
public class Wallet {

    @Id
    public int id;
    public int currencyId;
    @ManyToOne(fetch = FetchType.LAZY)
    public Currency currency;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "wallet")
    public Player player;

    public int customerId;
    public Timestamp date;
    public int amount;
    public MoneyVector vector;
    public PaymentReason reasonId;
    public int tradingId;
    //public virtual Trading Trading;
    public boolean donat;
}
