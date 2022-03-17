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
@Table(name = "clients")
@NamedEntityGraph(
        name = "clients-entity-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "bankAccounts", subgraph = "bankAccounts-sub-graph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "banks-sub-graph",
                        attributeNodes = {
                                @NamedAttributeNode("bankProducer"),
                        }
                )
        }
)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Pattern(regexp = "[A-Z][a-z]*", message = "Bank name must start with a capital letter")
    @Size(min = 2, max = 25, message = "Name length must be between 2 and 25")
    @NotEmpty(message = "Name must not be empty")
    private String name;

    @Column(name = "type")
    private ClientType type;

    @OneToMany(
            cascade = CascadeType.REMOVE,
            mappedBy = "owner",
            fetch = FetchType.LAZY
    )
    @Valid
    private Set<BankAccount> bankAccounts;

    @OneToMany(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            mappedBy = "sender"
    )
    @Valid
    private Set<Transaction> sentTransactions;

    @OneToMany(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY,
            mappedBy = "receiver"
    )
    @Valid
    private Set<Transaction> receivedTransactions;

    @Override
    public String toString() {
        return "Client{"
                + "id=" + id
                + ", name='" + name
                + '\'' + ", type=" + type
                + '}';
    }
}
