package ee.bcs.vali.it.controller;

import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;


import java.sql.SQLException;

public class ServicesHistoryRowMapper implements RowMapper<ServiceData>{
    @Override
    public ServiceData mapRow(ResultSet resultSet, int i) throws SQLException {
        ServiceData loggedMemberServicesHistory = new ServiceData();
        loggedMemberServicesHistory.setServiceFullName(resultSet.getString("full_name"));
        loggedMemberServicesHistory.setServiceRating(resultSet.getDouble("rating"));
        loggedMemberServicesHistory.setServiceDescription(resultSet.getString("service_description"));

        return loggedMemberServicesHistory;
    }

}





