package com.nnk.springboot.service;

import com.nnk.springboot.mapper.MapperDto;
import com.nnk.springboot.domain.Dto.TradeDto;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Get all trades
     *
     * @return a list of all trades
     */
    @Override
    public List<TradeDto> getAllTrades() {
        logger.info("Get all trade");
        List<Trade> tradeList = tradeRepository.findAllByOrderByTradeIdDesc();
        return tradeList.stream().map(MapperDto::convertToTradeDto)
                .collect(Collectors.toList());
    }

    /**
     * Get a trade
     *
     * @param id of the requested trade
     * @return trade found by id
     * @throws NotFoundException if trade id doesn't exist
     */
    @Override
    public TradeDto getTradeById(Integer id) {
        logger.info("Get a trade by ID");
        if (!tradeRepository.existsById(id)) {
            logger.error("Unable to get trade by id : " + id + " because doesn't exist");
            throw new NotFoundException("Trade id doesn't exist");
        }
        Trade trade = tradeRepository.getById(id);
        return MapperDto.convertToTradeDto(trade);
    }

    /**
     * Update a trade
     *
     * @param id trade id to update
     * @param tradeDto trade information to update
     * @return trade updated
     * @throws NotFoundException if trade id doesn't exist
     */
    @Override
    @Transactional
    public Trade updateTrade(Integer id, TradeDto tradeDto) {
        logger.info("Get a trade by ID");
        if (!tradeRepository.existsById(id)) {
            logger.error("Unable to get trade by id : " + id + " because doesn't exist");
            throw new NotFoundException("Trade id doesn't exist");
        }
        Trade tradeToUpdate = tradeRepository.getById(id);
        tradeToUpdate.setAccount(tradeDto.getAccount());
        tradeToUpdate.setType(tradeDto.getType());
        tradeToUpdate.setBuyQuantity(tradeDto.getBuyQuantity());
        logger.info("Trade updated");
        return tradeRepository.save(tradeToUpdate);
    }

    /**
     * Delete a trade by id
     *
     * @param id ID of trade to delete
     */
    @Override
    @Transactional
    public void deleteTrade(Integer id) {
        logger.info("Delete trade : " + id);
        tradeRepository.deleteById(id);
    }
}
