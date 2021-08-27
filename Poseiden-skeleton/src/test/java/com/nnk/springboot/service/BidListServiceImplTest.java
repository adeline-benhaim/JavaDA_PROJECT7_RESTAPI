package com.nnk.springboot.service;

import com.nnk.springboot.config.DataSourceTest;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Dto.BidListDto;
import com.nnk.springboot.repositories.BidListRepository;
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
public class BidListServiceImplTest {

    @Mock
    BidListRepository bidListRepository;
    @InjectMocks
    BidListServiceImpl bidListService;
    @InjectMocks
    DataSourceTest dataSourceTest;

    @BeforeEach
    void init() {
        dataSourceTest.clearBidListMocked();
        dataSourceTest.createBidListMocked();
    }

    @Test
    @DisplayName("Create new bid")
    void createBidTest() {

        //GIVEN
        BidListDto bidListDto = BidListDto.builder().bidListId(4).account("account4").type("type4").bidQuantity(4d).build();

        //WHEN
        bidListService.createBidList(bidListDto);

        //THEN
        Mockito.verify(bidListRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Get all bids")
    void getAllBidsTest() {

        //GIVEN
        List<BidList> bidList = dataSourceTest.getBidListListMocked();
        Mockito.when(bidListRepository.findAllByOrderByBidListIdDesc()).thenReturn(bidList);

        //THEN
        List<BidListDto> bidListDto = bidListService.getAllBidList();

        //WHEN
        assertEquals(bidList.size(), bidListDto.size());
        assertEquals(bidList.get(0).getAccount(), bidListDto.get(0).getAccount());
    }

    @Test
    @DisplayName("Get existing bid by id")
    void getExistingBidByIdTest() {

        //GIVEN
        Mockito.when(bidListRepository.existsById(1)).thenReturn(true);
        BidList bidList = dataSourceTest.getBidListListMocked().get(0);
        Mockito.when(bidListRepository.getById(1)).thenReturn(bidList);

        //THEN
        BidListDto bidListDto = bidListService.getBidListById(1);

        //WHEN
        assertEquals(bidList.getAccount(), bidListDto.getAccount());
    }

    @Test
    @DisplayName("Get unknown bid by id throw NotFoundException")
    void getUnknownBidByIdTest() {

        //GIVEN
        Mockito.when(bidListRepository.existsById(5)).thenReturn(false);

        //THEN

        //WHEN
        assertThrows(NotFoundException.class, () -> bidListService.getBidListById(5));
    }

    @Test
    @DisplayName("Update an existing bid")
    void updateExistingBidTest() {

        //GIVEN
        Mockito.when(bidListRepository.existsById(1)).thenReturn(true);
        BidList bidToUpdate = dataSourceTest.getBidListListMocked().get(0);
        Mockito.when(bidListRepository.getById(1)).thenReturn(bidToUpdate);
        BidListDto bidListDto = BidListDto.builder().bidListId(1).account("account1Updated").type("type1Updated").bidQuantity(1d).build();

        //THEN
        bidListService.updateBidList(1, bidListDto);

        //WHEN
        Mockito.verify(bidListRepository, Mockito.times(1)).save(bidToUpdate);
        assertEquals("account1Updated", bidToUpdate.getAccount());
    }

    @Test
    @DisplayName("Try to update an unknown bid throw NotFoundException")
    void updateUnknownBidTest() {

        //GIVEN
        Mockito.when(bidListRepository.existsById(5)).thenReturn(false);
        BidListDto bidListDto = BidListDto.builder().bidListId(1).account("account1Updated").type("type1Updated").bidQuantity(1d).build();

        //THEN

        //WHEN
        assertThrows(NotFoundException.class, () -> bidListService.updateBidList(5, bidListDto));
    }

    @Test
    @DisplayName("Delete a bid")
    void deleteBidTest() {

        //GIVEN

        //WHEN
        bidListService.deleteBidList(1);

        //THEN
        Mockito.verify(bidListRepository, Mockito.times(1)).deleteById(any());
    }
}
