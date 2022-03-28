package by.ganevich.csv.csvMapper;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import by.ganevich.csv.CsvWriter;
import by.ganevich.entity.Transaction;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvTransactionMapper implements CsvMapper<Transaction> {
    @Override
    public List<Transaction> toEntity(String fileName) throws IOException {
        CsvToBean csv = new CsvToBean();

        CSVReader csvReader = new CSVReader(new FileReader(fileName));

        List<Transaction> transactions = new ArrayList<>();
        List list = csv.parse(setColumnMapping(), csvReader);

        for (Object object : list) {
            Transaction transaction = (Transaction) object;
            transactions.add(transaction);
        }
        csvReader.close();
        return transactions;
    }

    public void toCsv(String fileName, Transaction transaction) throws IOException {
        String toCsv = transaction.getId().toString() + ","
                + transaction.getSender().getId().toString() + ","
                + transaction.getReceiver().getId().toString() + ","
                + transaction.getAmountOfMoney().toString() + ","
                + transaction.getDate().toString() + ",";
        CsvWriter csvWriter = new CsvWriter();
        csvWriter.writeCsv(fileName, toCsv);
    }

    private static ColumnPositionMappingStrategy setColumnMapping()
    {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(Transaction.class);
        String[] columns = new String[] {
                "senderId",
                "receiverId",
                "amountOfMoney",
                "senderAccountId",
                "receiverAccountId",
                "date"
        };
        strategy.setColumnMapping(columns);
        return strategy;
    }

}
