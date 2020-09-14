package ee.bcs.vali.it.controller;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WelcomeUserRowMapper implements RowMapper<ClientData> {
    @Override
    public ClientData mapRow(ResultSet resultSet, int i) throws SQLException {
        ClientData loggedUser = new ClientData();
        loggedUser.setFirstName(resultSet.getString("name"));
        loggedUser.setLastName(resultSet.getString("lastname"));
        return loggedUser;
    }
}
