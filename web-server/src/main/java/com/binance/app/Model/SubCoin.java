package com.binance.app.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class SubCoin {
    @Id
    private String symbol;
    private double price;

    public SubCoin(){}
    public SubCoin(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "symbol = " + symbol + '\'' +
                ", price = " + price +
                '}';
    }
}
