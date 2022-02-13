package by.ganevich.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
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
                fetch = FetchType.LAZY)
    @JoinTable(
            name = "clients_banks",
            joinColumns = @JoinColumn(name = "bank_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id")
    )
    @Transient
    private Set<Client> clients;

    @OneToMany(mappedBy = "bankProducer",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY)
    @Transient
    private Set<BankAccount> bankAccounts;

}
