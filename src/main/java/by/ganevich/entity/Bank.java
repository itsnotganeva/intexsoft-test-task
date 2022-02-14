package by.ganevich.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
//@Data
@Getter
@Setter
@Table(name = "banks")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "commission")
    private Double commission;

    @ManyToMany(cascade = CascadeType.DETACH,
                fetch = FetchType.EAGER)
    @JoinTable(
            name = "clients_banks",
            joinColumns = @JoinColumn(name = "clientId"),
            inverseJoinColumns = @JoinColumn(name = "bankId")
    )

    private Set<Client> clients;

    @OneToMany(mappedBy = "bankProducer",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER)

    private Set<BankAccount> bankAccounts;


    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", commission=" + commission +
                ", clients=" + clients +
                ", bankAccounts=" + bankAccounts +
                '}';
    }
}
