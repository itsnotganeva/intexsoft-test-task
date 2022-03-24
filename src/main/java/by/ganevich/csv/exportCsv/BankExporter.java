package by.ganevich.csv.exportCsv;

import by.ganevich.csv.csvMapper.CsvBankMapper;
import by.ganevich.entity.Bank;
import by.ganevich.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


@Component
@RequiredArgsConstructor
public class BankExporter extends CsvExporter {

    private final CsvBankMapper bankMapper;
    private final BankService bankService;

    @Override
    public void exportCsv(String fileName) throws IOException {
        List<Bank> banks = bankService.readBanks();
        for (Bank b : banks) {
        bankMapper.toCsv(fileName, b);
    }
    }
}
