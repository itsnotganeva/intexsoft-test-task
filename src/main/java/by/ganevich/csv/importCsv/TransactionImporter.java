package by.ganevich.csv.importCsv;

import by.ganevich.csv.csvMapper.CsvTransactionMapper;
import by.ganevich.entity.Transaction;
import by.ganevich.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TransactionImporter extends CsvImporter {

    private final TransactionService transactionService;
    private final CsvTransactionMapper transactionMapper;

    @Override
    public void importCsv(String fileName) throws FileNotFoundException {
        List<Transaction> transactions = transactionMapper
                .toEntity(fileName);
        for (Transaction t : transactions) {
            transactionService.saveTransaction(t);
        }
    }
}
