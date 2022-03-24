package by.ganevich.csv.exportCsv;

import by.ganevich.csv.csvMapper.CsvBankAccountMapper;
import by.ganevich.entity.BankAccount;
import by.ganevich.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BankAccountExporter extends CsvExporter {

    private final BankAccountService bankAccountService;
    private final CsvBankAccountMapper bankAccountMapper;

    @Override
    public void exportCsv(String fileName) throws IOException {
        List<BankAccount> bankAccounts = bankAccountService.readAll();
        for (BankAccount b : bankAccounts) {
        bankAccountMapper.toCsv(fileName, b);
    }
    }
}
