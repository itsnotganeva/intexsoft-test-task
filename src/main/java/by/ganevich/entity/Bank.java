package by.ganevich.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "banks")
@NamedEntityGraph(
        name = "banks-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("commissions")
        }
)
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
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "bank_id", nullable = false)
    private Set<Commission> commissions;

    @Override
    public String toString() {
        return "Bank{"
                + "id=" + id
                + ", name='" + name
                + '\'' + ", commissions=" + commissions
                + '}';
    }

}
