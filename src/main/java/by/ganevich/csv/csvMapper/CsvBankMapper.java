package by.ganevich.csv.csvMapper;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import by.ganevich.csv.CsvWriter;
import by.ganevich.csv.csvMapper.CsvMapper;
import by.ganevich.entity.Bank;
import by.ganevich.entity.Client;
import org.springframework.stereotype.Component;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvBankMapper implements CsvMapper {

    public List<Bank> toEntity(String fileName) throws FileNotFoundException {
        CsvToBean csv = new CsvToBean();

        CSVReader csvReader = new CSVReader(new FileReader(fileName));

        List<Bank> banks = new ArrayList<>();
        List list = csv.parse(setColumMapping(), csvReader);
        for (Object object : list) {
            Bank bank = (Bank) object;
            banks.add(bank);
        }
        return banks;
    }

    public void toCsv(String fileName, Bank bank) throws IOException {
        String toCsv = bank.getId().toString() + "," + bank.getName();
        CsvWriter csvWriter = new CsvWriter();
        csvWriter.writeCsv(fileName, toCsv);
    }

    private static ColumnPositionMappingStrategy setColumMapping()
    {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(Bank.class);
        String[] columns = new String[] {"bankName"};
        strategy.setColumnMapping(columns);
        return strategy;
    }
}
