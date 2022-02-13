package by.ganevich.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@Table(name = "bankAccounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency")
    private String currency;

    @Column(name = "ammountOfMoney")
    private Double amountOfMoney;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "clientId")
    private Client accountOwner;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "bankId")
    private Bank bankProducer;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(Double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public Client getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(Client accountOwner) {
        this.accountOwner = accountOwner;
    }

    public Bank getBankProducer() {
        return bankProducer;
    }

    public void setBankProducer(Bank bankProducer) {
        this.bankProducer = bankProducer;
    }
}
