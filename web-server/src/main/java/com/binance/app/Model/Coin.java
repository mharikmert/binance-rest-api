package com.binance.app.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Coin {
    @Id
    private long id;
    private String symbol;
    private double price;

    @Override
    public String toString() {
        return "Coin{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", price=" + price +
                '}';
    }
}
