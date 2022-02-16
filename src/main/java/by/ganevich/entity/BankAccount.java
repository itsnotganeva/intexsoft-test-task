package by.ganevich.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "bankAccounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency")
    private Currency currency;

    @Column(name = "ammountOfMoney")
    private Double amountOfMoney;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "clientId")
    private Client accountOwner;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "bankId")
    private Bank bankProducer;

    @OneToMany(cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER,
            mappedBy = "senderAccount")
    private Set<Transaction> sentTransactions;

    @OneToMany(cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER,
            mappedBy = "receiverAccount")
    private Set<Transaction> receivedTransactions;

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", currency='" + currency + '\'' +
                ", amountOfMoney=" + amountOfMoney +
                ", accountOwner=" + accountOwner +
                ", bankProducer=" + bankProducer +
                '}';
    }
}
