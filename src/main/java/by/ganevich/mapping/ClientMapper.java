package by.ganevich.mapping;

import by.ganevich.entity.Client;
import by.ganevich.entity.ClientType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientMapper implements RowMapper<Client>{

    @Override
    public Client toObject(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId(rs.getLong("id"));
        client.setName(rs.getString("name"));
        int ordinal = rs.getInt("type");

        client.setType(ClientType.getByOrdinal(ordinal));
        return client;
    }
}
