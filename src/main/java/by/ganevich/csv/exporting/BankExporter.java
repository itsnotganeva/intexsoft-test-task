package by.ganevich.csv.exporting;

import by.ganevich.csv.mapping.BaseCsvMapper;
import by.ganevich.csv.mapping.CsvBankCsvMapper;
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
