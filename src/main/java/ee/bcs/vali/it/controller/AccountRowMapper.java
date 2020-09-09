package ee.bcs.vali.it.controller;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class AccountRowMapper implements RowMapper<BankClientsSql>{
    @Override
    public BankClientsSql mapRow(ResultSet resultSet, int i) throws SQLException {
        BankClientsSql bankClient = new BankClientsSql();
        bankClient.setId(resultSet.getInt("id"));
        bankClient.setClientName(resultSet.getString("client_name"));
        bankClient.setClientLastName(resultSet.getString("client_lastname"));
        bankClient.setAccountNumber(resultSet.getString("account_nr"));
        bankClient.setBalance(resultSet.getBigDecimal("balance"));
        return bankClient;
        }
}
