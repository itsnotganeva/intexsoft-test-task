package by.ganevich.mapping;

import by.ganevich.entity.BankAccount;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BankAccountMapper implements RowMapper<BankAccount> {
    @Override
    public BankAccount toObject(ResultSet rs) throws SQLException {
//        BankAccount bankAccount = new BankAccount();
//        bankAccount.setId(rs.getLong("id"));
//        bankAccount.setCurrency(rs.getString("currency"));
//        bankAccount.setAmountOfMoney(rs.getDouble("ammountofmoney"));
//        bankAccount.setBankId(rs.getLong("bankid"));
//        bankAccount.setClientId(rs.getLong("clientid"));
       // return bankAccount;
        return null;
    }
}
