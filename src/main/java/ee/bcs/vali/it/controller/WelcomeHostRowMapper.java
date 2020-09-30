package ee.bcs.vali.it.controller;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WelcomeHostRowMapper implements RowMapper<HostData> {
    @Override
    public HostData mapRow(ResultSet resultSet, int i) throws SQLException {
        HostData loggedHost = new HostData();
        loggedHost.setHostName(resultSet.getString("firstname"));
        loggedHost.setHostLastName(resultSet.getString("lastname"));
        loggedHost.setHostRole(resultSet.getString("hostrole"));
        loggedHost.setHostLogin(resultSet.getString("hostlogin"));
        return loggedHost;
    }
}
