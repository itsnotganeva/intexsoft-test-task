package by.ganevich.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @Pattern(regexp = "[A-Z][a-z]*", message = "Bank name must start with a capital letter")
    @Size(min = 2, max = 25, message = "Name length must be between 2 and 25")
    @NotEmpty(message = "Name must not be empty")
    private String name;

    @OneToMany(
            mappedBy = "bankProducer",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    @Valid
    private Set<BankAccount> bankAccounts;

    @OneToMany(
            mappedBy = "bank",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
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
