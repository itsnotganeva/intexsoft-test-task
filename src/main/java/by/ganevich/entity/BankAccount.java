package by.ganevich.entity;

import by.ganevich.entity.enums.Currency;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "bank_accounts")
@NamedEntityGraph(
        name = "bankAccounts-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("owner"),
                @NamedAttributeNode(value = "bankProducer", subgraph = "banks-sub-graph"),
                @NamedAttributeNode("sentTransactions"),
                @NamedAttributeNode("receivedTransactions")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "banks-sub-graph",
                        attributeNodes = {
                                @NamedAttributeNode("commissions")
                        }
                )
        }
)
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "amount_of_money")
    private Double amountOfMoney;

    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "client_id")
    private Client owner;

    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "bank_id")
    private Bank bankProducer;

    @OneToMany(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            mappedBy = "senderAccount"
    )
    private Set<Transaction> sentTransactions;

    @OneToMany(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            mappedBy = "receiverAccount"
    )
    private Set<Transaction> receivedTransactions;

    @Override
    public String toString() {
        return "BankAccount{"
                + "currency=" + currency
                + ", amountOfMoney=" + amountOfMoney
                + ", bankProducer=" + bankProducer
                + '}';
    }
}
