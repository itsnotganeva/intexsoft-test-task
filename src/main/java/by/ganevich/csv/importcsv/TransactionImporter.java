package by.ganevich.csv.importcsv;

import by.ganevich.csv.csvmapper.BaseCsvMapper;
import by.ganevich.csv.csvmapper.CsvTransactionCsvMapper;
import by.ganevich.service.BaseService;
import by.ganevich.service.TransactionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class TransactionImporter extends CsvImporter {

    private final String fileName = "importTransactions.csv";
    private final TransactionService transactionService;
    private final CsvTransactionCsvMapper transactionMapper;

    @Override
    public BaseService getService() {
        return this.transactionService;
    }

    @Override
    public BaseCsvMapper getMapper() {
        return this.transactionMapper;
    }
}
