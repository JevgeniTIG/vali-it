package ee.bcs.vali.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccountRepository {

    @Autowired
    private NamedParameterJdbcTemplate dataBase;

    public void createUser(BigInteger id, String name, String lastname){
        String sql = "INSERT INTO customer(name, lastname) " +
                "VALUES (:name, :lastname)";
        Map<String, Object> paramMap1 = new HashMap<>();
        paramMap1.put("name", name);
        paramMap1.put("lastname", lastname);
        dataBase.update(sql, paramMap1);

    }


    public void createAccount(String account, BigDecimal balance, BigInteger customerId){
        String sql = "INSERT INTO accounts(account_nr, balance, customer_id) " +
                "VALUES (:accountNr, :balance, :customer_id)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("accountNr", account);
        paramMap.put("balance", balance);
        paramMap.put("customer_id", customerId);
        dataBase.update(sql, paramMap);
    }


    public void createStatement(String account,
                                BigDecimal deposit, BigDecimal withdraw, BigDecimal balance){
        String sql = "INSERT INTO transactions(account_id, account, deposit, withdraw, balance) " +
                "VALUES (:account_id, :account, :deposit, :withdraw, :balance)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("account", account);
        paramMap.put("deposit", deposit);
        paramMap.put("withdraw", withdraw);
        paramMap.put("balance", balance);
        dataBase.update(sql, paramMap);

    }


    public void insertDepositIntoTransactions(BigInteger accountId, String account, BigDecimal amount, BigDecimal balance) {

        String sql = "INSERT INTO transactions(account_id, account, deposit, balance) " +
                "VALUES(:account_id, :account, :deposit, :balance)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("account_id", accountId);
        paramMap.put("account", account);
        paramMap.put("deposit", amount);
        paramMap.put("balance", balance);
        dataBase.update(sql, paramMap);
    }

    public void updateBalance(String account, BigDecimal balance){
        String sql = "UPDATE accounts SET balance= :balance WHERE account_nr= :accountNumber";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("balance", balance);
        paramMap.put("accountNumber", account);
        dataBase.update(sql, paramMap);
    }


    public void insertWithdrawIntoTransactions(BigInteger accountId, String account, BigDecimal amount, BigDecimal balance){

        String sql = "INSERT INTO transactions(account_id, account, withdraw, balance) " +
                "VALUES(:account_id, :account, :withdraw, :balance)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("account_id", accountId);
        paramMap.put("account", account);
        paramMap.put("withdraw", amount);
        paramMap.put("balance", balance);
        dataBase.update(sql, paramMap);
    }

    public BigDecimal getBalance(String accountFrom){
        String sql = "SELECT balance FROM accounts WHERE account_nr= :accountNumber";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("accountNumber", accountFrom);
        return dataBase.queryForObject(sql, paramMap, BigDecimal.class);
    }

    public BigInteger getAccountIdByAccount(String account){
        String sql = "SELECT id FROM accounts WHERE account_nr= :account";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("account", account);
        return dataBase.queryForObject(sql, paramMap, BigInteger.class);
    }



    public List showStatementByAccount(BigInteger accountId){

        String sql = "SELECT account_id, account, deposit, withdraw, balance " +
                "FROM transactions WHERE account_id= :account_id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("account_id", accountId);
        List<ClientDataStatement> resultList = dataBase.query(sql, paramMap, new AccountRowMapperEfficient());
        return resultList;
    }



}
