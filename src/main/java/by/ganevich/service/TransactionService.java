package by.ganevich.service;

import by.ganevich.entity.Transaction;
import by.ganevich.repo.TransactionRepo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    private final TransactionRepo transactionRepo = TransactionRepo.getInstance();
    List<Transaction> transactions = new ArrayList<>();

    public void create(Transaction transaction) {
        transactionRepo.save(transaction);
    }

    public List<Transaction> read(java.sql.Date date1, Date date2) {
        transactions = transactionRepo.getByDate(date1, date2);
        return transactions;
    }
}
