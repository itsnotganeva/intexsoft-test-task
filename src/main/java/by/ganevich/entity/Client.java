package by.ganevich.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
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
            fetch = FetchType.LAZY)
    private Set<BankAccount> bankAccounts;

    @OneToMany(cascade = CascadeType.REMOVE,
                fetch = FetchType.LAZY,
    mappedBy = "sender")
    private Set<Transaction> sentTransactions;

    @OneToMany(cascade = CascadeType.REMOVE,
    fetch = FetchType.LAZY)
    private Set<Transaction> receivedTransactions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    public Set<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(Set<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public Set<Transaction> getSentTransactions() {
        return sentTransactions;
    }

    public void setSentTransactions(Set<Transaction> sentTransactions) {
        this.sentTransactions = sentTransactions;
    }

    public Set<Transaction> getReceivedTransactions() {
        return receivedTransactions;
    }

    public void setReceivedTransactions(Set<Transaction> receivedTransactions) {
        this.receivedTransactions = receivedTransactions;
    }
}
