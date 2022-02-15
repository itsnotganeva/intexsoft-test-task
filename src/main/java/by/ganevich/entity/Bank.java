package by.ganevich.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
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
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(id, bank.id) && Objects.equals(name, bank.name) && Objects.equals(commission, bank.commission) && Objects.equals(bankAccounts, bank.bankAccounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, commission, bankAccounts);
    }
}
