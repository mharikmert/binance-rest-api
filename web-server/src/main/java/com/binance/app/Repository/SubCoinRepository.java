package com.binance.app.Repository;

import com.binance.app.Model.SubCoin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCoinRepository extends JpaRepository<SubCoin, String> {
}
