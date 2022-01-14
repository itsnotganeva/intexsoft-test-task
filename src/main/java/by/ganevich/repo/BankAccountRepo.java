package by.ganevich.repo;

import by.ganevich.entity.Bank;
import by.ganevich.entity.BankAccount;
import by.ganevich.exception.EntityNotFoundException;
import by.ganevich.exception.EntitySaveException;
import by.ganevich.mapping.BankAccountMapper;
import by.ganevich.mapping.BankMapper;
import by.ganevich.mapping.RowMapper;
import by.ganevich.repo.jdbc.ConnectionPoolProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankAccountRepo extends AbstractCRUDRepository<BankAccount> {

    private static BankAccountRepo instance;

    private static final String INSERT_STATEMENT = "INSERT INTO bankaccount (currency, ammountofmoney, bankid, clientid) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_STATEMENT = "UPDATE bankaccount SET currency = ?, ammountofmoney = ?, bankid = ?, clientid = ? WHERE id = ?";

    public static final String SELECT_BY_CLIENT_ID = "SELECT * FROM bankaccount WHERE clientid = %d";
    public static final String SELECT_BY_CLIENT_AND_BANK = SELECT_BY_CLIENT_ID + " and bankid = %d";

    private RowMapper<BankAccount> rs;

    private List<BankAccount> bankAccounts = new ArrayList<>();

    private BankAccountRepo() {
        super(new BankAccountMapper(), "bankaccount");
        this.rs = new BankAccountMapper();
        instance = this;
    }

    public static BankAccountRepo getInstance() {
        if (instance == null) {
            BankAccountRepo.instance = new BankAccountRepo();
        }
        return instance;
    }

    public List<BankAccount> getByClientId(Long clientId) {
        try (Connection connection = ConnectionPoolProvider.getConnection()) {
            ResultSet resultSet = connection.createStatement()
                    .executeQuery(String.format(SELECT_BY_CLIENT_ID, clientId));

            while (resultSet.next()) {
                BankAccount bankAccount = rs.toObject(resultSet);
                bankAccounts.add(bankAccount);
            }
            return bankAccounts;
        } catch (SQLException e) {
            LOGGER.error("Something went wrong during entity retrieval by clientId=" + clientId, e);
            throw new EntityNotFoundException();
        }
    }

    public BankAccount getByClientAndBank(Long clientId, Long bankId) {
        try (Connection connection = ConnectionPoolProvider.getConnection()) {
            ResultSet resultSet = connection.createStatement()
                    .executeQuery(String.format(SELECT_BY_CLIENT_AND_BANK, clientId, bankId));

            if (resultSet.next()) {
                BankAccount bankAccount = rs.toObject(resultSet);
                return bankAccount;
            } else {
                return null;
            }

        } catch (SQLException e) {
            LOGGER.error("Something went wrong during entity retrieval by clientId=" + clientId, e);
            throw new EntityNotFoundException();
        }

    }

    public BankAccount save(BankAccount bankAccount) {
        PreparedStatement ps = null;
        try (Connection connection = ConnectionPoolProvider.getConnection()) {

            if (bankAccount.getId() == null) {
                ps = connection.prepareStatement(INSERT_STATEMENT, Statement.RETURN_GENERATED_KEYS);
            } else {
                ps = connection.prepareStatement(UPDATE_STATEMENT, Statement.RETURN_GENERATED_KEYS);
            }

            setValues(bankAccount, ps);

            if (bankAccount.getId() != null) {
                ps.setLong(5, bankAccount.getId());
            }

            if (ps.executeUpdate() != 1) {
                throw new EntitySaveException("Something went wrong");
            }

            ResultSet generatedKeys = ps.getGeneratedKeys();

            if (generatedKeys.next()) {
                bankAccount.setId(generatedKeys.getInt(1));
            }
            return bankAccount;

        } catch (SQLException e) {
            LOGGER.error("Something went wrong during entities retrieval", e);
            throw new EntitySaveException(e);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new EntitySaveException(e);
                }
            }
        }
    }

    public void setValues(BankAccount bankAccount, PreparedStatement ps) throws SQLException {
        ps.setString(1, bankAccount.getCurrency());
        ps.setDouble(2, bankAccount.getAmountOfMoney());
        ps.setLong(3, bankAccount.getBankId());
        ps.setLong(4, bankAccount.getClientId());
    }

}
