package by.ganevich.mapping;

import by.ganevich.entity.Bank;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BankMapper implements RowMapper<Bank> {
    @Override
    public Bank toObject(ResultSet rs) throws SQLException {
        Bank bank = new Bank();
//        bank.setId(rs.getLong("id"));
//        bank.setName(rs.getString("name"));
//        bank.setCommission(rs.getDouble("commission"));
        return bank;
    }
}
