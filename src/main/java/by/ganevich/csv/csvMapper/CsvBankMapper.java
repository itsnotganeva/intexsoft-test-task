package by.ganevich.csv.csvMapper;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import by.ganevich.csv.CsvWriter;
import by.ganevich.entity.Bank;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvBankMapper implements CsvMapper<Bank> {

    public List<Bank> toEntity(String fileName) throws IOException {
        CsvToBean csv = new CsvToBean();

        CSVReader csvReader = new CSVReader(new FileReader(fileName));

        List<Bank> banks = new ArrayList<>();
        List list = csv.parse(setColumnMapping(), csvReader);
        for (Object object : list) {
            Bank bank = (Bank) object;
            banks.add(bank);
        }
        csvReader.close();
        return banks;
    }

    public void toCsv(String fileName, Bank bank) throws IOException {
        String toCsv = bank.getId().toString() + "," + bank.getName();
        CsvWriter csvWriter = new CsvWriter();
        csvWriter.writeCsv(fileName, toCsv);
    }

    private static ColumnPositionMappingStrategy setColumnMapping()
    {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(Bank.class);
        String[] columns = new String[] {"bankName"};
        strategy.setColumnMapping(columns);
        return strategy;
    }
}
