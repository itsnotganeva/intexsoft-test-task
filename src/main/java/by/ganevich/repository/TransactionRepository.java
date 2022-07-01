package by.ganevich.repository;

import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByDateBetweenAndSenderAccount(Date dateBefore, Date dateAfter, BankAccount bankAccount);

    List<Transaction> findAllByDateBetweenAndReceiverAccount(Date dateBefore, Date dateAfter, BankAccount bankAccount);

    List<Transaction> findAllByDateBetweenAndSenderAccountOrReceiverAccount(Date dateBefore, Date dateAfter,
                                                                            BankAccount senderNumber, BankAccount receiverNumber);

    List<Transaction> findAllBySenderAccountOrReceiverAccount(BankAccount senderAccount, BankAccount receiverAccount);

}
