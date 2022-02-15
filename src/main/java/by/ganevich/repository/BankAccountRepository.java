package by.ganevich.repository;

import by.ganevich.entity.Bank;
import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    BankAccount findBankAccountByAccountOwnerAndBankProducer(Client owner, Bank bankProducer);

    Set<BankAccount> findBankAccountByAccountOwner(Client owner);
}
