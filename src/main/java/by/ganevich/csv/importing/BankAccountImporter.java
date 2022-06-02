package by.ganevich.csv.importing;

import by.ganevich.csv.mapping.BaseCsvMapper;
import by.ganevich.csv.mapping.CsvBankAccountCsvMapper;
import by.ganevich.service.BankAccountService;
import by.ganevich.service.BaseService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class BankAccountImporter extends CsvImporter {

    private final String fileName = "importBankAccounts.csv";
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
