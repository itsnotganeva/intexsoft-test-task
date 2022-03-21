package by.ganevich.csv.csvMapper;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import by.ganevich.csv.CsvWriter;
import by.ganevich.entity.Bank;
import by.ganevich.entity.BankAccount;

import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvBankAccountMapper implements CsvMapper{
    @Override
    public List<BankAccount> toEntity(String fileName) throws FileNotFoundException {
        CsvToBean csv = new CsvToBean();

        CSVReader csvReader = new CSVReader(new FileReader(fileName));

        List<BankAccount> bankAccounts = new ArrayList<>();
        List list = csv.parse(setColumMapping(), csvReader);
        for (Object object : list) {
            BankAccount bankAccount = (BankAccount) object;
            bankAccounts.add(bankAccount);
        }
        return bankAccounts;
    }

    public void toCsv(String fileName, BankAccount bankAccount) throws IOException {
        String toCsv = bankAccount.getId().toString() + ","
                        + bankAccount.getNumber().toString() + ","
                        + bankAccount.getOwner().getId().toString() + ","
                        + bankAccount.getBankProducer().getId().toString() + ","
                        + bankAccount.getCurrency().toString() + ","
                        + bankAccount.getAmountOfMoney().toString();
        CsvWriter csvWriter = new CsvWriter();
        csvWriter.writeCsv(fileName, toCsv);
    }

    private static ColumnPositionMappingStrategy setColumMapping()
    {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(BankAccount.class);
        String[] columns = new String[] {
                "number",
                "currency",
                "amountOfMoney",
                "bankId",
                "clientId"
        };
        strategy.setColumnMapping(columns);
        return strategy;
    }
}
