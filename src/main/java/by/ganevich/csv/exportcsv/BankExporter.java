package by.ganevich.csv.exportcsv;

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
public class BankExporter extends CsvExporter {

    private final CsvBankCsvMapper bankMapper;
    private final BankService bankService;

    private final String fileName = "exportBanks.csv";

    @Override
    public BaseService getService() {
        return this.bankService;
    }

    @Override
    public BaseCsvMapper getMapper() {
        return this.bankMapper;
    }

}
