package by.ganevich.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
//@Data
@Getter
@Setter
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private ClientType type;

    @OneToMany(cascade = CascadeType.REMOVE,
            mappedBy = "accountOwner",
            fetch = FetchType.EAGER)
    private Set<BankAccount> bankAccounts;

    @ManyToMany(cascade = CascadeType.DETACH,
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "clients_banks",
            joinColumns = @JoinColumn(name = "clientId"),
            inverseJoinColumns = @JoinColumn(name = "bankId")
    )
    private Set<Bank> banks;

    @OneToMany(cascade = CascadeType.REMOVE,
                fetch = FetchType.EAGER,
    mappedBy = "sender")

    private Set<Transaction> sentTransactions;

    @OneToMany(cascade = CascadeType.REMOVE,
    fetch = FetchType.EAGER,
    mappedBy = "receiver")

    private Set<Transaction> receivedTransactions;

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", bankAccounts=" + bankAccounts +
                ", banks=" + banks +
                ", sentTransactions=" + sentTransactions +
                ", receivedTransactions=" + receivedTransactions +
                '}';
    }
}
