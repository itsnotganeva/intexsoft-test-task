package by.ganevich.repo;

import by.ganevich.entity.Client;
import by.ganevich.exception.EntitySaveException;
import by.ganevich.mapping.ClientMapper;
import by.ganevich.repo.jdbc.ConnectionPoolProvider;

import java.sql.*;

public class ClientRepo extends AbstractCRUDRepository<Client>{

    private static ClientRepo instance;

    private static final String INSERT_STATEMENT = "INSERT INTO client (name, type) VALUES (?, ?)";
    private static final String UPDATE_STATEMENT = "UPDATE client SET name = ?, type = ? WHERE id = ?";

    private ClientRepo() {
        super(new ClientMapper(), "client");
        instance = this;
    }

    public static ClientRepo getInstance() {
        if (instance == null) {
            ClientRepo.instance = new ClientRepo();
        }
        return instance;
    }

    public Client save(Client client) {


        PreparedStatement ps = null;
        try (Connection connection = ConnectionPoolProvider.getConnection()) {

            if (client.getId() == null) {
                ps = connection.prepareStatement(INSERT_STATEMENT, Statement.RETURN_GENERATED_KEYS);
            } else {
                ps = connection.prepareStatement(UPDATE_STATEMENT, Statement.RETURN_GENERATED_KEYS);
            }

            setValues(client, ps);

            if (client.getId() != null) {
                ps.setLong(3, client.getId());
            }

            if (ps.executeUpdate() != 1) {
                throw new EntitySaveException("Something went wrong");
            }

            ResultSet generatedKeys = ps.getGeneratedKeys();

            if (generatedKeys.next()) {
                client.setId(generatedKeys.getLong(1));
            }
            return client;

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

    public void setValues(Client client, PreparedStatement ps) throws SQLException {
        ps.setString(1, client.getName());
        ps.setInt(2, client.getType().ordinal());
    }

}
