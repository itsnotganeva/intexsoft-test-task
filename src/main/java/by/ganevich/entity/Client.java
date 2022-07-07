package by.ganevich.entity;

import by.ganevich.entity.enums.ClientType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "clients")
@NamedEntityGraph(
        name = "clients-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("bankAccounts")
        }
)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ClientType type;

    @OneToMany(
            cascade = CascadeType.REMOVE,
            mappedBy = "owner",
            fetch = FetchType.LAZY
    )
    private Set<BankAccount> bankAccounts;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Override
    public String toString() {
        return "Client{"
                + "id=" + id
                + ", name='" + name
                + '\'' + ", type=" + type
                + '}';
    }
}
