package com.nnk.springboot.service;

import com.nnk.springboot.domain.Dto.TradeDto;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exception.NotFoundException;

import java.util.List;

public interface TradeService {

    /**
     * Create a new trade
     *
     * @param tradeDto new trade to save
     * @return trade saved
     */
    Trade createTrade(TradeDto tradeDto);

    /**
     * Get all trades
     *
     * @return a list of all trades
     */
    List<TradeDto> getAllTrades();

    /**
     * Get a trade
     *
     * @param id of the requested trade
     * @return trade found by id
     * @throws NotFoundException if trade id doesn't exist
     */
    TradeDto getTradeById(Integer id);

    /**
     * Update a trade
     *
     * @param id trade id to update
     * @param tradeDto trade information to update
     * @return trade updated
     * @throws NotFoundException if trade id doesn't exist
     */
    Trade updateTrade(Integer id, TradeDto tradeDto);

    /**
     * Delete a trade by id
     *
     * @param id ID of trade to delete
     */
    void deleteTrade(Integer id);
}
