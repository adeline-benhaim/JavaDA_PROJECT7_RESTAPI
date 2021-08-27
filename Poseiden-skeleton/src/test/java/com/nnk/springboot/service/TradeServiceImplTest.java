package com.nnk.springboot.service;

import com.nnk.springboot.config.DataSourceTest;
import com.nnk.springboot.domain.Dto.TradeDto;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class TradeServiceImplTest {

    @Mock
    TradeRepository tradeRepository;
    @InjectMocks
    TradeServiceImpl tradeService;
    @InjectMocks
    DataSourceTest dataSourceTest;

    @BeforeEach
    void init() {
        dataSourceTest.clearTradeListMocked();
        dataSourceTest.createTradeListMocked();
    }

    @Test
    @DisplayName("Create new trade")
    void createTradeTest() {

        //GIVEN
        TradeDto tradeTdo = TradeDto.builder().tradeId(4).account("account4").type("type4").buyQuantity(4d).build();

        //WHEN
        tradeService.createTrade(tradeTdo);

        //THEN
        Mockito.verify(tradeRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Get all trades")
    void getAllTradesTest() {

        //GIVEN
        List<Trade> tradeList = dataSourceTest.getTradeListMocked();
        Mockito.when(tradeRepository.findAllByOrderByTradeIdDesc()).thenReturn(tradeList);

        //THEN
        List<TradeDto> tradeDtoList = tradeService.getAllTrades();

        //WHEN
        assertEquals(tradeList.size(), tradeDtoList.size());
        assertEquals(tradeList.get(0).getAccount(), tradeDtoList.get(0).getAccount());
    }

    @Test
    @DisplayName("Get existing trade by id")
    void getExistingTradeByIdTest() {

        //GIVEN
        Mockito.when(tradeRepository.existsById(1)).thenReturn(true);
        Trade trade = dataSourceTest.getTradeListMocked().get(0);
        Mockito.when(tradeRepository.getById(1)).thenReturn(trade);

        //THEN
        TradeDto tradeDto = tradeService.getTradeById(1);

        //WHEN
        assertEquals(trade.getAccount(), tradeDto.getAccount());
    }

    @Test
    @DisplayName("Get unknown trade by id throw NotFoundException")
    void getUnknownTradeByIdTest() {

        //GIVEN
        Mockito.when(tradeRepository.existsById(5)).thenReturn(false);

        //THEN

        //WHEN
        assertThrows(NotFoundException.class, () -> tradeService.getTradeById(5));
    }

    @Test
    @DisplayName("Update an existing trade")
    void updateExistingTradeTest() {

        //GIVEN
        Mockito.when(tradeRepository.existsById(1)).thenReturn(true);
        Trade tradeToUpdate = dataSourceTest.getTradeListMocked().get(0);
        Mockito.when(tradeRepository.getById(1)).thenReturn(tradeToUpdate);
        TradeDto tradeDto = TradeDto.builder().tradeId(1).account("account1Updated").type("type1Updated").buyQuantity(1d).build();

        //THEN
        tradeService.updateTrade(1, tradeDto);

        //WHEN
        Mockito.verify(tradeRepository, Mockito.times(1)).save(tradeToUpdate);
        assertEquals("account1Updated", tradeToUpdate.getAccount());
    }

    @Test
    @DisplayName("Try to update an unknown trade throw NotFoundException")
    void updateUnknownTradeTest() {

        //GIVEN
        Mockito.when(tradeRepository.existsById(5)).thenReturn(false);
        TradeDto tradeDto = TradeDto.builder().tradeId(1).account("account1Updated").type("type1Updated").buyQuantity(1d).build();

        //THEN

        //WHEN
        assertThrows(NotFoundException.class, () -> tradeService.updateTrade(5, tradeDto));
    }

    @Test
    @DisplayName("Delete a trade")
    void deleteTradeTest() {

        //GIVEN

        //WHEN
        tradeService.deleteTrade(1);

        //THEN
        Mockito.verify(tradeRepository, Mockito.times(1)).deleteById(any());
    }
}
