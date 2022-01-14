package by.ganevich.repo;

import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.entity.Transaction;
import by.ganevich.exception.EntityNotFoundException;
import by.ganevich.exception.EntitySaveException;
import by.ganevich.mapping.BankAccountMapper;
import by.ganevich.mapping.BankMapper;
import by.ganevich.mapping.RowMapper;
import by.ganevich.mapping.TransactionMapper;
import by.ganevich.repo.jdbc.ConnectionPoolProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepo extends AbstractCRUDRepository<Transaction>{

    private static TransactionRepo instance;
    private static final String INSERT_STATEMENT = "INSERT INTO transaction_operation (senderid, recipientid, amountofmoney, date) VALUES (?, ?, ?, ?)";
    public static final String SELECT_BY_DATE = "SELECT * FROM bankaccount WHERE date > %d and date < %d";

    private RowMapper<Transaction> rs;
    private List<Transaction> transactions = new ArrayList<>();

    private TransactionRepo() {
        super(new TransactionMapper(), "transaction_operation");
        this.rs = new TransactionMapper();
        instance = this;
    }

    public static TransactionRepo getInstance() {
        if (instance == null) {
            TransactionRepo.instance = new TransactionRepo();
        }
        return instance;
    }

    public List<Transaction> getByDate(Date min, Date max) {
        try (Connection connection = ConnectionPoolProvider.getConnection()) {
            ResultSet resultSet = connection.createStatement()
                    .executeQuery(String.format(SELECT_BY_DATE, min, max));

            if (resultSet.next()) {
                while (resultSet.next()) {
                    Transaction transaction = rs.toObject(resultSet);
                    transactions.add(transaction);
                }
                return transactions;
            } else {
                return null;
            }

        } catch (SQLException e) {
            LOGGER.error("Something went wrong during entity retrieval", e);
            throw new EntityNotFoundException();
        }

    }

    public Transaction save(Transaction transaction) {
        PreparedStatement ps = null;
        try (Connection connection = ConnectionPoolProvider.getConnection()) {

            if (transaction.getId() == null) {
                ps = connection.prepareStatement(INSERT_STATEMENT, Statement.RETURN_GENERATED_KEYS);
            }
            setValues(transaction, ps);

            if (transaction.getId() != null) {
                ps.setLong(5, transaction.getId());
            }

            if (ps.executeUpdate() != 1) {
                throw new EntitySaveException("Something went wrong");
            }

            ResultSet generatedKeys = ps.getGeneratedKeys();

            if (generatedKeys.next()) {
                transaction.setId(generatedKeys.getLong(1));
            }
            return transaction;

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

    public void setValues(Transaction transaction, PreparedStatement ps) throws SQLException {
        ps.setLong(1, transaction.getSenderId());
        ps.setLong(2, transaction.getRecipientId());
        ps.setDouble(3, transaction.getAmountOfMoney());
        ps.setDate(4, (Date) transaction.getDate());
    }
}
