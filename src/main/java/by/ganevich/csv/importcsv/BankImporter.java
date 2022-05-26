package by.ganevich.csv.importcsv;

import by.ganevich.csv.csvmapper.BaseCsvMapper;
import by.ganevich.csv.csvmapper.CsvBankCsvMapper;
import by.ganevich.service.BankService;
import by.ganevich.service.BaseService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class BankImporter extends CsvImporter {

    private final String fileName = "importBanks.csv";

    private final CsvBankCsvMapper bankMapper;
    private final BankService bankService;

    @Override
    public BaseService getService() {
        return this.bankService;
    }

    @Override
    public BaseCsvMapper getMapper() {
        return this.bankMapper;
    }
}
