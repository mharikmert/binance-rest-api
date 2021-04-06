package com.binance.app.Model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Coin {
    @Id
    private String symbol;
    private double price;

    public Coin(){}
    public Coin(String symbol, double price) {
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
