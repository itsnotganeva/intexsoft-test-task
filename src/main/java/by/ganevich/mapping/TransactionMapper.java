package by.ganevich.mapping;

import by.ganevich.entity.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionMapper implements RowMapper<Transaction>{

    @Override
    public Transaction toObject(ResultSet rs) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(rs.getLong("id"));
        transaction.setSenderId(rs.getLong("senderid"));
        transaction.setRecipientId(rs.getLong("recipientid"));
        transaction.setAmountOfMoney(rs.getDouble("amountofmoney"));
        transaction.setDate(rs.getDate("date"));
        return transaction;
    }
}
