package by.ganevich.repository;

import by.ganevich.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    @Query(value = "select * from bankaccounts where bankId=?1 and clientId=?2", nativeQuery = true)
    BankAccount findBankAccountByBankIdAndClientId(Long bankId, Long clientId);

    @Query(value = "select * from bankaccounts where clientId=?1", nativeQuery = true)
    Set<BankAccount> findBankAccountByClientId(Long clientId);
}
