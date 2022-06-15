package by.ganevich.csv.mapping;

import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import by.ganevich.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class CsvTransactionCsvMapper extends BaseCsvMapper<Transaction> {

    @Override
    public String getCsvString(Transaction transaction) {
        return transaction.getId().toString() + ","
                + transaction.getSenderAccount().getNumber().toString() + ","
                + transaction.getReceiverAccount().getNumber().toString() + ","
                + transaction.getAmountOfMoney().toString() + ","
                + transaction.getDate().toString() + ",";
    }

    @Override
    public ColumnPositionMappingStrategy setColumnMapping() {
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
