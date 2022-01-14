package by.ganevich.repo;

import by.ganevich.exception.EntityDeleteException;
import by.ganevich.exception.EntityNotFoundException;
import by.ganevich.exception.EntityRetrieveException;
import by.ganevich.exception.EntitySaveException;
import by.ganevich.mapping.RowMapper;
import by.ganevich.repo.jdbc.ConnectionPoolProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractCRUDRepository<T> {

    protected final Logger LOGGER;
    private static final String SELECT_STATEMENT = "SELECT * FROM %s";
    private static final String SELECT_BY_ID_SQL = SELECT_STATEMENT + " WHERE id = %d";
    private static final String DELETE_STATEMENT = "DELETE FROM %s WHERE id = %d";

    private RowMapper<T> rs;
    private String tableName;

    public AbstractCRUDRepository(RowMapper<T> rs, String tableName) {
        this.rs = rs;
        this.tableName = tableName;
        this.LOGGER = LoggerFactory.getLogger(AbstractCRUDRepository.class);
    }

    public T getById(Long id) {
        try (Connection connection = ConnectionPoolProvider.getConnection()) {
            ResultSet resultSet = connection.createStatement()
                    .executeQuery(String.format(SELECT_BY_ID_SQL, tableName, id));

            if (resultSet.next()) {
                T entity = rs.toObject(resultSet);
                return entity;
            } else {
                return null;
            }

        } catch (SQLException e) {
            LOGGER.error("Something went wrong during entity retrieval by id=" + id, e);
            throw new EntityNotFoundException();
        }
    }

    public List<T> findAll() {
        try (Connection connection = ConnectionPoolProvider.getConnection()) {
            ResultSet resultSet = connection.createStatement()
                    .executeQuery(String.format(SELECT_STATEMENT, tableName));

            List<T> entities = new ArrayList<>();

            while (resultSet.next()) {
                entities.add(rs.toObject(resultSet));
            }
            return entities;

        } catch (SQLException e) {
            LOGGER.error("Something went wrong during entities retrieval", e);
            throw new EntityRetrieveException();
        }
    }

    public void deleteById(Long id) {
        try (Connection connection = ConnectionPoolProvider.getConnection()) {
            int rowsAffected = connection.createStatement()
                    .executeUpdate(String.format(DELETE_STATEMENT, tableName, id));

            if (rowsAffected != 1) {
                throw new EntityDeleteException("Entity not deleted");
            }

        } catch (SQLException e) {
            LOGGER.error("Something went wrong during entities retrieval", e);
            throw new EntityRetrieveException();
        }
    }

}
