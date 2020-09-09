package ee.bcs.vali.it.controller;

import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapperEfficient implements RowMapper<ClientDataStatement> {
    @Override
    public ClientDataStatement mapRow(ResultSet resultSet, int i) throws SQLException {
        ClientDataStatement clientData = new ClientDataStatement();
        clientData.setAccountId(BigInteger.valueOf(resultSet.getInt("account_id")));
        clientData.setAccount(resultSet.getString("account"));
        clientData.setDeposit(resultSet.getBigDecimal("deposit"));
        clientData.setWithdraw(resultSet.getBigDecimal("withdraw"));
        clientData.setBalance(resultSet.getBigDecimal("balance"));
        return clientData;
    }
}






