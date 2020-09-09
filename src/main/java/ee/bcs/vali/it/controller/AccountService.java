package ee.bcs.vali.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    //Creates user
    public void createUser(BigInteger id, String name, String lastName){
        accountRepository.createUser(id, name, lastName);
    }

    //Creates account related to user
    public void createAccount(String account, BigDecimal balance, BigInteger customerId){
        accountRepository.createAccount(account, balance, customerId);
    }

    //Updates table 'accounts'
    public void updateBalance(String account, BigDecimal balance){
        accountRepository.updateBalance(account, balance);
    }

    //Shows balance on specified account
    public BigDecimal showBalanceByAccount(String acc){
        return accountRepository.getBalance(acc);
    }

    //Deposits money to specified account
    public void depositFunds(BigInteger accountId, String accTo, BigDecimal amount, BigDecimal balance){
        BigDecimal accountAfterDeposit = accountRepository.getBalance(accTo).add(amount);
        BigInteger idOfAccount = accountRepository.getAccountIdByAccount(accTo);
        accountRepository.insertDepositIntoTransactions(idOfAccount, accTo, amount, accountAfterDeposit);
        updateBalance(accTo, accountAfterDeposit);
    }

    //Withdraws money from specified account
    boolean withdrawCanBeDone;
    public boolean withdrawFunds(BigInteger accountId, String accFrom, BigDecimal amount, BigDecimal balance ){
        BigDecimal accountToWithdraw= accountRepository.getBalance(accFrom);
        if(accountToWithdraw.compareTo(amount) >= 0 ){
            withdrawCanBeDone = true;
            BigDecimal newBalance = accountToWithdraw.subtract(amount);
            BigInteger idOfAccount = accountRepository.getAccountIdByAccount(accFrom);
            accountRepository.insertWithdrawIntoTransactions(idOfAccount, accFrom, amount, newBalance);
            updateBalance(accFrom, newBalance);
            return withdrawCanBeDone;
        } return withdrawCanBeDone = false;
    }

    //Transfers money between accounts
    public void transferMoney(BigInteger accountId, String account, String accountFrom, String accountTo, BigDecimal amount,
                              BigDecimal balanceTo, BigDecimal balanceFrom, BigDecimal balance){
        BigInteger idOfAccountFrom = accountRepository.getAccountIdByAccount(accountFrom);
        withdrawFunds(idOfAccountFrom, accountFrom, amount, balance);

        if (withdrawCanBeDone == true){
            BigInteger idOfAccountTo = accountRepository.getAccountIdByAccount(accountTo);
            depositFunds(idOfAccountTo, accountTo, amount, balance);
        }
    }

    //Shows all transactions of specified account from table 'transactions'
    public List showStatementByAccount(BigInteger customerId){
        return accountRepository.showStatementByAccount(customerId);
    }


}
