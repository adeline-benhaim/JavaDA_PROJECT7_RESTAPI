package com.nnk.springboot.service;

import com.nnk.springboot.domain.Dto.TradeDto;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeServiceImpl implements TradeService {
    private static final Logger logger = LoggerFactory.getLogger(TradeServiceImpl.class);

    @Autowired
    private TradeRepository tradeRepository;

    /**
     * Create a new trade
     *
     * @param tradeDto new trade to save
     * @return trade saved
     */
    @Override
    public Trade createTrade(TradeDto tradeDto) {
        logger.info("Create a new trade");
        Trade newTrade = Trade.builder()
                .account(tradeDto.getAccount())
                .type(tradeDto.getType())
                .buyQuantity(tradeDto.getBuyQuantity())
                .build();
        return tradeRepository.save(newTrade);
    }


}
