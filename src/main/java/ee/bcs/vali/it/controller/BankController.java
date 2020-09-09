package ee.bcs.vali.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;


@RestController
public class BankController {

    private static final Map<String, Double> bankClients = new HashMap<>();


    // @Autowired
    @Autowired
    private NamedParameterJdbcTemplate dataBase;

    // Fill the table 'accounts' with rows of data with
    @GetMapping("addclient")
    public void addClient(@RequestBody BankClientsSql customer){
        String sql = "INSERT INTO accounts(client_name, client_lastname, account_nr, balance) " +
                "VALUES (:clientName, :clientLastName, :accountNr, :balance)";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("clientName", customer.getClientName());
        paramMap.put("clientLastName", customer.getClientLastName());
        paramMap.put("accountNr", customer.getAccountNumber());
        paramMap.put("balance", customer.getBalance());
        dataBase.update(sql, paramMap);


    }
    // Shows a balance on the specified account
    @GetMapping("show_balance_by_acc/{accNo}")
    public BigDecimal showMoneyOnSpecifiedAccount(@PathVariable("accNo") String accountNo){
        String sql = "SELECT balance FROM accounts WHERE account_nr= :accountNr";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("accountNr", accountNo);
        BigDecimal balanceOnAccount = dataBase.queryForObject(sql, paramMap, BigDecimal.class);
        return balanceOnAccount;
    }

    // Deposits money onto specified account

    @GetMapping("deposit/{accNo}/{amount}")
    public void depositOnToAccount(@PathVariable("accNo") String accountNo,
                                         @PathVariable("amount") BigDecimal moneyToDeposit){
        String sql = "UPDATE accounts SET balance= balance + :balance WHERE account_nr = :accountNr";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("accountNr", accountNo);
        paramMap.put("balance", moneyToDeposit);

        dataBase.update(sql, paramMap);
    }

    // Withdraws money from specified account
    @GetMapping("withdraw/{accNo}/{amount}")
    public void withdrawFromAccount(@PathVariable("accNo") String accountNo,
                                    @PathVariable("amount") BigDecimal moneyToWithdraw){


        String sql = "UPDATE accounts SET balance= balance - :balance WHERE " +
                "account_nr = :accountNr and balance >= :balance";
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("accountNr", accountNo);
        paramMap.put("balance", moneyToWithdraw);

        dataBase.update(sql, paramMap);

    }

    // Transfers money from one account to another

    @GetMapping("transfer/{accNoFrom}/{accNoTo}/{amount}")
    public String transferMoney(@PathVariable("accNoFrom") String accountNoFrom,
                                    @PathVariable("accNoTo") String accountNoTo,
                                    @PathVariable("amount") BigDecimal moneyToTransfer){



        String sql = "SELECT balance FROM accounts WHERE account_nr= :accountNrFrom";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("accountNrFrom", accountNoFrom);
        BigDecimal balanceOnAccount = dataBase.queryForObject(sql, paramMap, BigDecimal.class);

        int compareResult = (balanceOnAccount.compareTo(moneyToTransfer));

        if (compareResult >= 0){

            String sql1 = "UPDATE accounts SET balance= balance - :balance WHERE " +
                    "account_nr = :accountNrFrom";
            Map<String, Object> paramMap1 = new HashMap<>();

            paramMap1.put("accountNrFrom", accountNoFrom);
            paramMap1.put("balance", moneyToTransfer);
            dataBase.update(sql1, paramMap1);


            String sql2 = "UPDATE accounts SET balance= balance + :balance WHERE " +
                    "account_nr = :accountNrTo";
            Map<String, Object> paramMap2 = new HashMap<>();

            paramMap2.put("accountNrTo", accountNoTo);
            paramMap2.put("balance", moneyToTransfer);

            dataBase.update(sql2, paramMap2);

        }
        else{
            return "Not enough sufficient funds!";

        }
        return "Transaction complete!";

    }

    // Get all clients from the database
    @GetMapping("entiredatabase")
    public List showAllClients(){
        String sql= "SELECT * FROM accounts";
        List<BankClientsSql> resultList = dataBase.query(sql, new HashMap<>(), new AccountRowMapper());
        return resultList;

    }




    @GetMapping("bankclientsgetall")
    public Map<String, Double> getAllBankClients() {
        return bankClients;
    }

    @PostMapping("bankclientspost")
    public void addBankClient(@RequestBody BankClients customer) {
        bankClients.put(customer.getAccountNumber(), customer.getBalance());
    }

    // shows amount of money on specified account
    @GetMapping("bankclientsget/{accNo}")
    public double getAccNoGetBalance(@PathVariable("accNo") String accNo) {
        return bankClients.get(accNo);
    }

    // deposits money
    @PutMapping("bankclientsdeposit")
    public void depositMoney(@RequestBody BankClients customer) {

        bankClients.put(customer.getAccountNumber(), bankClients.get(customer.getAccountNumber())
                + customer.getBalance());
    }

    // withdraws money
    @PutMapping("bankclientswithdraw")
    public String withdrawMoney(@RequestBody BankClients customer) {
        double newBalance = bankClients.get(customer.getAccountNumber())
                - customer.getBalance();
        try {
            if (newBalance >= 0) {
                bankClients.put(customer.getAccountNumber(), newBalance);
            } else {
                throw new RuntimeException("No sufficient funds on the account!");
            }
        } catch (Exception e1) {
            return e1.getMessage();
        }


        return "ok";
    }

    // transfers money

    @PutMapping("bankclientstransfer/{accNoFrom}/{accNoTo}/{amount}")
    public String transferMoney(@PathVariable("accNoFrom") String accNoFrom,
                                @PathVariable("accNoTo") String accNoTo,
                                @PathVariable("amount") double amount) {

        double accNoFromBalanceNew = bankClients.get(accNoFrom) - amount;

        try {
            if (accNoFromBalanceNew >= 0) {
                bankClients.put(accNoFrom, accNoFromBalanceNew);
                bankClients.put(accNoTo, bankClients.get(accNoTo) + amount);
            } else {
                throw new RuntimeException("No sufficient funds");
            }
        } catch (Exception e2) {
            return e2.getMessage();
        }

        return "ok";
    }

    // Game "Guess number"
    Random random = new Random();
    int i = random.nextInt(100);
    int count = 0;
    @PutMapping("game/{number}")


    public String randomGame(@PathVariable("number") int userInput) {
        // TODO kirjuta mäng mis võtab suvalise numbri 0-100, mille kasutaja peab ära arvama
        // iga kord pärast kasutaja sisestatud täis arvu peab programm ütlema kas number oli suurem või väiksem
        // ja kasutaja peab saama uuesti arvata
        // numbri ära arvamise korral peab programm välja trükkima mitu katset läks numbri ära arvamiseks

        while (true) {
            int userGuess = userInput;

            count++;
            if (userGuess < 0 || userGuess > 100) {
                return "Your guess is out of bounds!";

            } else if (userGuess == i) {
                return "Congratulations! You WIN! The guessed number is " + userGuess +
                        ". You managed to guess the number with " + count + " attempts";
            } else if (userGuess < i) {
                if(count < 3){
                    return " Attempt nr: " + count + ". Guessed number is BIGGER. Try again: ";
                }
                else{
                    return " Attempt nr: " + count + ". Common! You are almost there. \nHowever, guessed number is BIGGER. Try again: ";
                }

            } else if (userGuess > i) {
                if(count < 3){
                    return " Attempt nr: " + count + ". Guessed number is SMALLER. Try again: ";
                }
                else{
                    return " Attempt nr: " + count + ". Common! You are almost there. \nHowever, guessed number is SMALLER. Try again: ";
                }
            }

            return "Invalid value";
        }

    }


}
