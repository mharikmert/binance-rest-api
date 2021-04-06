package com.binance.app.Controller;

import com.binance.app.Repository.CoinRepository;
import com.binance.app.Model.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CoinRestController {
    // get api key and secret from application properties file
    @Value("${apiKey}")
    private String apiKey;

    @Value("${secretKey}")
    private String secretKey;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    //injects restTemplate bean to fetch data
    private RestTemplate restTemplate;
    @Autowired
    public void setRestTemplate (RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    //inject the coin repository to use CRUD operations
    private CoinRepository coinRepository;

    @Autowired
    public void setCoinRepository(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    @RequestMapping(value = "/api/v1/coins", method = RequestMethod.POST)
    public ResponseEntity<?> saveCoins(@RequestBody Coin coin) {
        coinRepository.save(coin);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "api/v1/coins", method = RequestMethod.GET)
    public ResponseEntity<?> coins(){
        return ResponseEntity.ok(coinRepository.findAll());
    }

    //fetches all bitcoin current price and saves to postgre
    @RequestMapping(value = "/api/v1/fetchAllBtc", method = RequestMethod.GET)
    public ResponseEntity<?> fetchAllBTC() throws JSONException {
        String url = "https://api.binance.com/api/v3/ticker/price";
        //returns List of JSON objects -> {"symbol": "SOMEBTC", "price": "price"}
        Object[] data = restTemplate.getForObject(url, Object[].class); // json directly?
        assert data != null;
        //converts obj to json array
        JSONArray obj = new JSONArray(data);
        //json parse
        for(int i = 0; i < obj.length(); i++) {
            JSONObject jsonObject = (JSONObject) obj.get(i); // have price and symbol
            //type conversion
            String symbol = (String) jsonObject.get("symbol");
            double price = Double.parseDouble((String) jsonObject.get("price"));
            //save data as coin
            coinRepository.save(new Coin(symbol, price));
        }
        return ResponseEntity.ok(data);
    }
}
