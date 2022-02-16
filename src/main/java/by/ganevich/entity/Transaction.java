package by.ganevich.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "senderAccountId")
    private BankAccount senderAccount;

    @ManyToOne(cascade = CascadeType.DETACH,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "sender")
    private Client sender;

    @ManyToOne(cascade = CascadeType.DETACH,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "recipientAccountId")
    private BankAccount receiverAccount;

    @Column(name = "amountofmoney")
    private Double amountOfMoney;

    @Column(name = "date")
    private Date date;

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", senderAccount=" + senderAccount +
                ", sender=" + sender +
                ", receiverAccount=" + receiverAccount +
                ", amountOfMoney=" + amountOfMoney +
                ", date=" + date +
                '}';
    }
}
