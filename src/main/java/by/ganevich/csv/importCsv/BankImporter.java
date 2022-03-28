package by.ganevich.csv.importCsv;

import by.ganevich.csv.csvMapper.CsvBankMapper;
import by.ganevich.entity.Bank;
import by.ganevich.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BankImporter extends CsvImporter {

    private final CsvBankMapper bankMapper;
    private final BankService bankService;

    @Override
    public void importCsv(String fileName) throws IOException {
        List<Bank> banks = bankMapper.toEntity(fileName);
        for (Bank b : banks) {
            bankService.saveBank(b);
        }
    }
}
