package com.nnk.springboot.service;

import com.nnk.springboot.domain.Dto.TradeDto;
import com.nnk.springboot.domain.Trade;

public interface TradeService {

    /**
     * Create a new trade
     *
     * @param tradeDto new trade to save
     * @return trade saved
     */
    Trade createTrade(TradeDto tradeDto);
}
