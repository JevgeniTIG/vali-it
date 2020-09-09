package ee.bcs.vali.it.controller;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BetController {

    //Creates a map of players making their bets
    private static final Map<String, Accounts> allPlayers= new HashMap<>();

    // Creates players one by one
    @PostMapping("players_post")
    public void addPlayer(@RequestBody BettingParties player){
        allPlayers.put(player.getName(), player.getAccount());
    }
    // Shows all players
    @GetMapping("players")
    public Map<String, Accounts> showAllPlayers(){
        return allPlayers;
    }

    // The betting game
    @PutMapping("the_game")
    public String lets_play(@RequestBody BettingParties player){
        BigDecimal startPrice = new BigDecimal(100);
        //BigDecimal buyerAccBalance;




    return "ok";
    }


}
