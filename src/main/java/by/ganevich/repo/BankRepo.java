package by.ganevich.repo;

import by.ganevich.entity.Bank;
import by.ganevich.exception.EntitySaveException;
import by.ganevich.mapping.BankMapper;
import by.ganevich.repo.jdbc.ConnectionPoolProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Map;

public class BankRepo extends AbstractCRUDRepository<Bank>{

    private static final Logger LOGGER = LoggerFactory.getLogger(BankRepo.class);

    private static BankRepo instance;

    private static final String INSERT_STATEMENT = "INSERT INTO bank (name, commission) VALUES (?, ?)";
    private static final String UPDATE_STATEMENT = "UPDATE bank SET name = ?, commission = ? WHERE id = ?";

    private static final String INSERT_INTO_BANK_CLIENT = "INSERT INTO client_bank (bankid, clientid) VALUES (?, ?)";

    private BankRepo() {
        super(new BankMapper(), "bank");
        instance = this;
    }

    public static BankRepo getInstance() {
        if (instance == null) {
            BankRepo.instance = new BankRepo();
        }
        return instance;
    }

    public Bank save(Bank bank) {
        PreparedStatement ps = null;
        try (Connection connection = ConnectionPoolProvider.getConnection()) {

            if (bank.getId() == null) {
                ps = connection.prepareStatement(INSERT_STATEMENT, Statement.RETURN_GENERATED_KEYS);
            } else {
                ps = connection.prepareStatement(UPDATE_STATEMENT, Statement.RETURN_GENERATED_KEYS);
            }

            setValues(bank, ps);

            if (bank.getId() != null) {
                ps.setLong(3, bank.getId());
            }

            if (ps.executeUpdate() != 1) {
                throw new EntitySaveException("Something went wrong");
            }

            ResultSet generatedKeys = ps.getGeneratedKeys();

            if (generatedKeys.next()) {
                bank.setId(generatedKeys.getInt(1));
            }
            return bank;

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

    public void addClient(Long bankId, Long clientId) {
        PreparedStatement ps = null;
        try (Connection connection = ConnectionPoolProvider.getConnection()) {

            ps = connection.prepareStatement(INSERT_INTO_BANK_CLIENT, Statement.RETURN_GENERATED_KEYS);

            setValues(bankId, clientId, ps);


            if (ps.executeUpdate() != 1) {
                throw new EntitySaveException("Something went wrong");
            }


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

    public void setValues(Bank bank, PreparedStatement ps) throws SQLException {
        ps.setString(1, bank.getName());
        ps.setDouble(2, bank.getCommission());
    }

    public void setValues(Long bankId, Long clientId, PreparedStatement ps) throws SQLException {
        ps.setLong(1, bankId);
        ps.setLong(2, clientId);
    }

}
