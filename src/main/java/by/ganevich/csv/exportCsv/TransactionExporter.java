package by.ganevich.csv.exportCsv;

import by.ganevich.csv.csvMapper.CsvTransactionMapper;
import by.ganevich.entity.Transaction;
import by.ganevich.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TransactionExporter extends CsvExporter {

    private final TransactionService transactionService;
    private final CsvTransactionMapper transactionMapper;

    @Override
    public void exportCsv(String fileName) throws IOException {
        List<Transaction> transactions = transactionService.readAll();
        for (Transaction t : transactions) {
        transactionMapper.toCsv(fileName, t);
    }
    }
}
