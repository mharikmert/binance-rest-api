package com.binance.app.Controller;

import com.binance.app.Model.SubCoin;
import com.binance.app.Repository.CoinRepository;
import com.binance.app.Model.Coin;
import com.binance.app.Repository.SubCoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    @Autowired
    private RestTemplate restTemplate;
    //inject entity repositories for CRUD operations
    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private SubCoinRepository subCoinRepository;


    @RequestMapping(value = "/api/v1/subCoins", method = RequestMethod.POST)
    public ResponseEntity<?> saveCoins(@RequestBody SubCoin subCoin) {
        System.out.println(subCoin);
        subCoinRepository.save(subCoin);
        return ResponseEntity.ok().build();
    }
    @RequestMapping(value = "/api/v1/subCoins", method = RequestMethod.GET)
    public ResponseEntity<?> showSubCoins() {
        return ResponseEntity.ok(subCoinRepository.findAll());
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

    //serves requested coin info dynamically
    @RequestMapping(
            value = "/api/v1/klines",
            method = RequestMethod.GET)
    public ResponseEntity<?> avgPrice(
            //required true by default
            @RequestParam("symbol") String symbol,
            @RequestParam("interval") String interval,
            @RequestParam(value = "startTime",required = false) Long startTime,
            @RequestParam(value = "endTime", required = false) Long endTime,
            @RequestParam(value = "limit",required = false) Long limit)
    {

        //dynamically created url with parameters
        String url = "https://api.binance.com/api/v3/klines?symbol=" + symbol + "&interval=" + interval;
        if(startTime != null) url += "&startTime=" + startTime;
        if(endTime != null) url += "&endTime=" + endTime;
        if(limit != null) url += "&limit=" + limit;
        try{
            //fetch data from url and serveV
            Object response = restTemplate.getForObject(url, Object.class);
            //return ResponseEntity.ok(restTemplate.getForObject(url, Object.class));
            return ResponseEntity.ok(response);
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //serves average price in last 5 min
    @RequestMapping(value = "api/v1/avgPrice", method = RequestMethod.GET)
    public ResponseEntity<?> averagePrice(@RequestParam("symbol") String symbol){
        System.out.println(symbol);
        String url = "https://api.binance.com/api/v3/avgPrice?symbol=" + symbol;
        Object response = restTemplate.getForObject(url, Object.class);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(
            value = "api/v1/hourAvg",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> hourAverage(@RequestParam("symbol") String symbol) throws JSONException {
        String url = "https://api.binance.com/api/v3/klines?symbol=" + symbol + "&interval=1h&limit=1";
        //fetches hourly data
        Object [] response = restTemplate.getForObject(url, Object[].class);
        assert response != null;
        JSONArray obj = new JSONArray(response);
        JSONArray jsonArray = (JSONArray) obj.get(0);
        double averagePrice = ( Double.parseDouble((String) jsonArray.get(2)) +
                                Double.parseDouble((String) jsonArray.get(3)) ) / 2;


        //produces json object
        JSONObject json = new JSONObject();
        json.put("symbol", symbol);
        json.put("averagePrice",averagePrice);

        return ResponseEntity.ok(String.valueOf(json));
    }
}
