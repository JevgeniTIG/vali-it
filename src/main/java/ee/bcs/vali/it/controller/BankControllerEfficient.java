package ee.bcs.vali.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

//@RequestMapping ("bank")
@RestController
public class BankControllerEfficient {
    @Autowired
    private AccountService accountService;

    //Posts new customer to table 'customer'
    @PostMapping("enter_new_customer_efficient")
    public void createUser(@RequestBody ClientData request){
        accountService.createUser(request.getCustomerId(), request.getFirstName(),
                request.getLastName());
    }
    //Posts new account related to customer to table 'accounts'
    @PostMapping("enter_new_account_efficient")
    public void createAccount(@RequestBody ClientData request){
        accountService.createAccount(request.getAccount(), request.getBalance(),
                request.getCustomerId());
    }

    //Deposits money through 'put' onto specified account
    @PutMapping("deposit_efficient")
    public void depositFunds(@RequestBody ClientData request){
        accountService.depositFunds(request.getAccountId(), request.getAccount(),
                request.getAmount(), request.getBalance());
    }
    //Withdraws money through 'put' from specified account
    @PutMapping("withdraw_efficient")
    public void withdrawFunds(@RequestBody ClientData request){
        accountService.withdrawFunds(request.getAccountId(), request.getAccount(),
                request.getAmount(), request.getBalance());


    }
    //Shows balance on specified account
    @PostMapping("getbalance_efficient")
    public BigDecimal getBalanceByAccountNumber(@RequestBody ClientData request){
        return accountService.showBalanceByAccount(request.getAccount());

    }
    //Transfers money between accounts
    @PutMapping("transfer_efficient")
    public void transferFunds(@RequestBody ClientData request){
        accountService.transferMoney(request.getAccountId(), request.getAccount(), request.getAccountFrom(),
                request.getAccountTo(), request.getAmount(), request.getBalance(), request.getBalance(), request.getBalance());
    }
    //Shows statement of specified account
    @PostMapping("showstatement_efficient")
    public List showStatementByAccount(@RequestBody ClientData request) {
        return accountService.showStatementByAccount(request.getAccountId());
    }

}
