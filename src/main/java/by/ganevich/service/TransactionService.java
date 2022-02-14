package by.ganevich.service;

import by.ganevich.entity.Client;
import by.ganevich.entity.Transaction;
import by.ganevich.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.sql.Date;
import java.util.Set;

@Component
@AllArgsConstructor
@Slf4j
public class TransactionService {

    private TransactionRepository transactionRepository;

    public void saveTransaction(Double sumOfMoney, Client sender, Client receiver) {
        Transaction transaction = new Transaction();
        long millis=System.currentTimeMillis();
        transaction.setDate(new Date(millis));
        transaction.setAmountOfMoney(sumOfMoney);
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transactionRepository.save(transaction);
    }

    public Set<Transaction> readAllByDateAndSender(Date dateBefore, Date dateAfter, Client sender) {
        return transactionRepository.findAllByDateBetweenAndAndSender(dateBefore, dateAfter, sender);
    }
}
