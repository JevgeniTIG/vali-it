package ee.bcs.vali.it.controller;

import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;

public class SuitableServicesRowMapper implements RowMapper<ServiceData>{
    @Override
    public ServiceData mapRow(ResultSet resultSet, int i) throws SQLException{
        ServiceData suitableServices = new ServiceData();
        suitableServices.setServiceName(resultSet.getString("service_name"));
        suitableServices.setServiceDescription(resultSet.getString("service_description"));
        suitableServices.setServiceDuration(resultSet.getString("service_duration"));
        suitableServices.setServicePrice(resultSet.getBigDecimal("service_price"));
        suitableServices.setServicePaymentMethod(resultSet.getString("payment_method"));
        suitableServices.setServiceRoomType(resultSet.getString("room_type"));
        suitableServices.setServiceAddress(resultSet.getString("location"));
        suitableServices.setServiceFullName(resultSet.getString("full_name"));

        return suitableServices;
    }
}
