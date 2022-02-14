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
    @JoinColumn(name = "senderId")
    private Client sender;

    @ManyToOne(cascade = CascadeType.DETACH,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "recipientId")
    private Client receiver;

    @Column(name = "amountofmoney")
    private Double amountOfMoney;

    @Column(name = "date")
    private Date date;

//    @Override
//    public String toString() {
//        return "Transaction{" +
//                "id=" + id +
//                ", sender=" + sender +
//                ", receiver=" + receiver +
//                ", amountOfMoney=" + amountOfMoney +
//                ", date=" + date +
//                '}';
//    }
}
