package by.ganevich.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "transactions")
@NamedEntityGraph(
        name = "transactions-entity-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "senderAccount", subgraph = "bankAccounts-sub-graph"),
                @NamedAttributeNode(value = "receiverAccount", subgraph = "bankAccounts-sub-graph"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "bankAccounts-sub-graph",
                        attributeNodes = {
                                @NamedAttributeNode("owner"),
                                @NamedAttributeNode(value = "bankProducer", subgraph = "banks-sub-graph")
                        }
                ),
                @NamedSubgraph(
                        name = "banks-sub-graph",
                        attributeNodes = {
                                @NamedAttributeNode("commissions")
                        }
                )
        }
)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "sender_account_id")
    private BankAccount senderAccount;

    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "receiver_account_id")
    private BankAccount receiverAccount;

    @Column(name = "amount_of_money")
    private Double amountOfMoney;

    @Column(name = "date")
    private Date date;

    @Override
    public String toString() {
        return "Transaction{"
                + "id=" + id
                + ", amountOfMoney=" + amountOfMoney
                + ", date=" + date
                + '}';
    }
}
