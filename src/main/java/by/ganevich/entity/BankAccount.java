package by.ganevich.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "bankAccounts")
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
    @NotNull
    private Integer number;

    @Column(name = "currency")
    private Currency currency;

    @Column(name = "amountOfMoney")
    @NotNull
    private Double amountOfMoney;

    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "clientId")
    @Valid
    private Client owner;

    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "bankId")
    @Valid
    private Bank bankProducer;

    @OneToMany(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            mappedBy = "senderAccount"
    )
    @Valid
    private Set<Transaction> sentTransactions;

    @OneToMany(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            mappedBy = "receiverAccount"
    )
    @Valid
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
