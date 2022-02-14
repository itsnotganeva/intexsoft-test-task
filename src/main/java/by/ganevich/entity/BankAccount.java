package by.ganevich.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "bankAccounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency")
    private String currency;

    @Column(name = "ammountOfMoney")
    private Double amountOfMoney;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "clientId")
    private Client accountOwner;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "bankId")
    private Bank bankProducer;

//    @Override
//    public String toString() {
//        return "BankAccount{" +
//                "id=" + id +
//                ", currency='" + currency + '\'' +
//                ", amountOfMoney=" + amountOfMoney +
//                ", accountOwner=" + accountOwner +
//                ", bankProducer=" + bankProducer +
//                '}';
//    }
}
