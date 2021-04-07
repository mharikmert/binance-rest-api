# Binance Rest API
## General API Information
- This is a simple rest api that fetches data from public endpoints of https://api.binance.com and manipulate these data for different purposes.   
- The base endpoint for  this project is: http://localhost:8080
- All endpoints return either a JSON object or array.

## Installation
```
~$ git clone https://github.com/mharikmert/binance-rest-api

```
## Running Locally
Set your environment variables to application.properties file under the resources as in sample-application.properties file.

In the web-server directory you can use run configuration of your IDE
or you can build a jar and run. 

## Build with
```
~/binance-rest-api/web-server$ gradle build
~/binance-rest-api/web-server/build/libs$ java -jar app-0.0.1.SNAPSHOT.jar
```

In the client directory you can run the react app
```
~/binance-rest-api/client$ npm start
```

Check http://localhost:8080

## Available Endpoints
 <br>

### Current price all
Current values of all the symbols
```
GET /api/v1/fetchAll
```
**Response:**
```javascript
{
"symbol": "ETHBTC",
"price": "0.03508800"
},
{
"symbol": "LTCBTC",
"price": "0.00387100"
},
{
"symbol": "BNBBTC",
"price": "0.00663030"
},
{
"symbol": "NEOBTC",
"price": "0.00106600"
},
{
"symbol": "QTUMETH",
"price": "0.00822000"
},
.
.
.
```

### Current price of spesific coins
These six are shown in home page with current price

```
GET /api/v1/subCoins
 ```

**Response**
```javascript
{
"symbol": "ETHBTC",
"price": "0.03508800"
},
{
"symbol": "LTCBTC",
"price": "0.00387100"
},
{
"symbol": "BNBBTC",
"price": "0.00663030"
},
{
"symbol": "XRPBTC",
"price": "0.03508800"
},
{
"symbol": "BCHBTC",
"price": "0.00387100"
},
```

### Current average price

Current average price for a symbol.
```
GET /api/v1/avgPrice
```

**Parameters:**

Name | Type | Mandatory | Description
------------ | ------------ | ------------ | ------------
symbol | STRING | YES |


**Response:**
```javascript
{
  "mins": 5,
  "price": "9.35751834"
}
```

### Hourly average price
Average price for a symbol in last hour.
```
GET /api/v1/hourAvg
```

**Parameters:**

Name | Type | Mandatory | Description
------------ | ------------ | ------------ | ------------
symbol | STRING | YES |


**Response:**
```javascript
{
  "symbol": "ETHBTC",
  "averagePrice": "0.00352"
}
```


### Kline/Candlestick data
```
GET /api/v1/klines
```
Kline/candlestick bars for a symbol.
Klines are uniquely identified by their open time.


**Parameters:**

Name | Type | Mandatory | Description
------------ | ------------ | ------------ | ------------
symbol | STRING | YES |
interval | ENUM | YES |
startTime | LONG | NO |
endTime | LONG | NO |
limit | INT | NO | Default 500; max 1000.

* If startTime and endTime are not sent, the most recent klines are returned.

**Response:**
```javascript
[
  [
    1499040000000,      // Open time
    "0.01634790",       // Open
    "0.80000000",       // High
    "0.01575800",       // Low
    "0.01577100",       // Close
    "148976.11427815",  // Volume
    1499644799999,      // Close time
    "2434.19055334",    // Quote asset volume
    308,                // Number of trades
    "1756.87402397",    // Taker buy base asset volume
    "28.46694368",      // Taker buy quote asset volume
    "17928899.62484339" // Ignore.
  ]
]
