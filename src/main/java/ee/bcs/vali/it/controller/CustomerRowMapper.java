package ee.bcs.vali.it.controller;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<ClientData> {
    @Override
    public ClientData mapRow(ResultSet resultSet, int i) throws SQLException{
        ClientData clientData = new ClientData();
        clientData.setLogin(resultSet.getString("login"));
        clientData.setPassword(resultSet.getString("password"));
        return clientData;
    }
}
