package by.ganevich.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

    @OneToMany(
            mappedBy = "bankProducer",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    private Set<BankAccount> bankAccounts;

    @OneToMany(
            mappedBy = "bank",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    @Fetch(FetchMode.JOIN)
    private Set<Commission> commissions;

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", commissions=" + commissions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(id, bank.id) && Objects.equals(name, bank.name) && Objects.equals(bankAccounts, bank.bankAccounts) && Objects.equals(commissions, bank.commissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, bankAccounts, commissions);
    }
}
