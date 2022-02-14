package by.ganevich.repository;

import by.ganevich.entity.Client;
import by.ganevich.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Set;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Set<Transaction> findAllByDateBetweenAndAndSender(Date dateBefore, Date dateAfter, Client client);
}
