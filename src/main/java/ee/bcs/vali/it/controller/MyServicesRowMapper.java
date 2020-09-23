package ee.bcs.vali.it.controller;

import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;


import java.sql.SQLException;

public class MyServicesRowMapper implements RowMapper<ServiceData>{
    @Override
    public ServiceData mapRow(ResultSet resultSet, int i) throws SQLException {
        ServiceData loggedHostServices = new ServiceData();
        loggedHostServices.setServiceName(resultSet.getString("service_name"));
        loggedHostServices.setServiceDescription(resultSet.getString("service_description"));
        loggedHostServices.setServiceDuration(resultSet.getString("service_duration"));
        loggedHostServices.setServicePrice(resultSet.getBigDecimal("service_price"));
        loggedHostServices.setServicePaymentMethod(resultSet.getString("payment_method"));
        loggedHostServices.setServiceRoomType(resultSet.getString("room_type"));
        loggedHostServices.setServiceAddress(resultSet.getString("location"));
        loggedHostServices.setServiceId(new BigInteger(resultSet.getString("id")));
        return loggedHostServices;
    }

}





