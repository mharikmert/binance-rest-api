package com.binance.app.Controller;

import com.binance.app.Repository.CoinRepository;
import com.binance.app.Model.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoinRestController {
    // get api key and secret from application properties file
    @Value("${apiKey}")
    private String apiKey;

    @Value("${secretKey}")
    private String secretKey;

    //inject the coin repository to use CRUD operations
    private CoinRepository coinRepository;

    @Autowired
    public void setCoinRepository(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    @RequestMapping(value = "/api/v1/coins", method = RequestMethod.GET)
    public ResponseEntity<?> saveCoins(@RequestBody Coin coin) {
        coinRepository.save(coin);
        return ResponseEntity.ok().build();
    }
}
