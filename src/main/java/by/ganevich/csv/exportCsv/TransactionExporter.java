package by.ganevich.csv.exportCsv;

import by.ganevich.csv.csvMapper.BaseCsvMapper;
import by.ganevich.csv.csvMapper.CsvTransactionCsvMapper;
import by.ganevich.service.BaseService;
import by.ganevich.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionExporter extends CsvExporter {

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
