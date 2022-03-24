package by.ganevich.csv.importCsv;

import by.ganevich.csv.csvMapper.CsvBankAccountMapper;
import by.ganevich.entity.BankAccount;
import by.ganevich.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BankAccountImporter extends CsvImporter {

    private final BankAccountService bankAccountService;
    private final CsvBankAccountMapper bankAccountMapper;

    @Override
    public void importCsv(String fileName) throws FileNotFoundException {
        List<BankAccount> bankAccounts = bankAccountMapper
                .toEntity(fileName);
        for (BankAccount b : bankAccounts) {
            bankAccountService.saveBankAccount(b);
        }
    }
}
