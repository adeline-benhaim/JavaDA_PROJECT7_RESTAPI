package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {

    /**
     * Find a list of trades sorted by id desc
     *
     * @return a list of all trades sorted by id desc
     */
    List<Trade> findAllByOrderByTradeIdDesc();

    /**
     * Find a trade
     *
     * @param id of the requested trade
     * @return trade found by id
     */
    Trade findTradeByTradeId(Integer id);
}
