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

    public String customerLogin(String login){
        String sql ="SELECT password FROM customer WHERE login= :login";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("login", login);
        return dataBase.queryForObject(sql, paramMap, String.class);
    }


    public List welcomeUser(String login){
        //Content of the table 'customer': name, lastname
        String sql = "SELECT name, lastname " +
                "FROM customer WHERE login= :login";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("login", login);
        List<ClientData> resultList = dataBase.query(sql, paramMap, new WelcomeUserRowMapper());
        return resultList;
    }


    public String getBalanceLogged(String login){
        String sql ="SELECT balance FROM accounts WHERE customer_id = (SELECT id FROM customer WHERE login= :login)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("login", login);
        return dataBase.queryForObject(sql, paramMap, String.class);
    }


    public List showStatementLogged(String login){
        //Content of the table 'transactions': account_id, account, deposit, withdraw, balance
        String sql = "SELECT * FROM transactions WHERE account_id = (SELECT id FROM customer WHERE login= :login)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("login", login);
        List<ClientDataStatement> resultList = dataBase.query(sql, paramMap, new AccountRowMapperEfficient());
        return resultList;
    }


    public void createUser(BigInteger id, String name, String lastname, String login, String password){
        String sql = "INSERT INTO customer(name, lastname, login, password) " +
                "VALUES (:name, :lastname, :login, :password)";
        Map<String, Object> paramMap1 = new HashMap<>();
        paramMap1.put("name", name);
        paramMap1.put("lastname", lastname);
        paramMap1.put("login", login);
        paramMap1.put("password", password);
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
        //Content of the table 'transactions': account_id, account, deposit, withdraw, balance
        String sql = "SELECT * " +
                "FROM transactions WHERE account_id= :account_id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("account_id", accountId);
        List<ClientDataStatement> resultList = dataBase.query(sql, paramMap, new AccountRowMapperEfficient());
        return resultList;
    }



}
