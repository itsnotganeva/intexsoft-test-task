package by.ganevich.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "transactions")
@NamedEntityGraph(
        name = "transactions-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("sender"),
                @NamedAttributeNode("receiver")
        }
)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "senderId")
    @Valid
    private Client sender;

    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "receiverId")
    @Valid
    private Client receiver;

    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "senderAccountId")
    @Valid
    private BankAccount senderAccount;

    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "receiverAccountId")
    @Valid
    private BankAccount receiverAccount;

    @Column(name = "amountOfMoney")
    @NotNull
    private Double amountOfMoney;

    @Column(name = "date")
    private Date date;

    @Override
    public String toString() {
        return "Transaction{"
                + "id=" + id
                + ", sender=" + sender
                + ", receiver=" + receiver
                + ", amountOfMoney=" + amountOfMoney
                + ", date=" + date
                + '}';
    }
}
