package by.ganevich.csv.importCsv;

import by.ganevich.csv.csvMapper.BaseCsvMapper;
import by.ganevich.csv.csvMapper.CsvBankAccountCsvMapper;
import by.ganevich.service.BankAccountService;
import by.ganevich.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BankAccountImporter extends CsvImporter {

    private final BankAccountService bankAccountService;
    private final CsvBankAccountCsvMapper bankAccountMapper;

    @Override
    public BaseService getService() {
        return this.bankAccountService;
    }

    @Override
    public BaseCsvMapper getMapper() {
        return this.bankAccountMapper;
    }
}
