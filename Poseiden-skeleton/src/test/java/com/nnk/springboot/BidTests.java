package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Dto.BidListDto;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class BidTests {

    @Autowired
    private BidListRepository bidListRepository;
    @Autowired
    private BidListServiceImpl bidListService;

    @Test
    @DisplayName("Get a list of bids")
    public void getAllBidsTest() {

        //GIVEN
        List<BidList> bidList = bidListRepository.findAllByOrderByBidListIdDesc();

        //WHEN
        List<BidListDto> bidListDto = bidListService.getAllBidList();

        //THEN
        assertEquals(bidList.size(), bidListDto.size());
        Assertions.assertTrue(bidList.size() > 0);
        assertEquals(12, bidList.get(0).getBidQuantity());
    }

    @Test
    @DisplayName("Get a bid by id")
    public void getBidByIdTest() {

        //GIVEN
        BidList bidList1 = bidListRepository.findBidListByBidListId(1);

        //WHEN
        BidListDto bidListDto1 = bidListService.getBidListById(1);

        //THEN
        assertEquals(bidList1.getAccount(), bidListDto1.getAccount());

    }

    @Test
    @DisplayName("Update a new bid")
    public void updateABidTest() {

        //GIVEN
        BidListDto bidListDtoUpdated = BidListDto.builder()
                .bidListId(2)
                .account("Account Test Updated")
                .type("Type Test Updated")
                .bidQuantity(20d)
                .build();

        //WHEN
        BidList bidList = bidListService.updateBidList(2, bidListDtoUpdated);

        //THEN
        assertEquals(bidList.getBidQuantity(), 20d);
        assertEquals(bidList.getAccount(), "Account Test Updated");
    }

    @Test
    @DisplayName("Create a new bid")
    public void createABidTest() {

        //GIVEN
        BidListDto bidListDto = BidListDto.builder()
                .account("Account Test")
                .type("Type Test")
                .bidQuantity(10d)
                .build();

        //WHEN
        BidList bidList = bidListService.createBidList(bidListDto);

        //THEN
        assertEquals(bidList.getBidQuantity(), 10d);
        assertEquals(bidList.getAccount(), "Account Test");
    }

    @Test
    @DisplayName("Delete a bid")
    public void deleteABidTest() {

        //GIVEN
        int sizeBeforeDeleting = bidListService.getAllBidList().size();

        //WHEN
        bidListService.deleteBidList(5);
        int sizeAfterDeleting = bidListService.getAllBidList().size();

        //THEN
        Assertions.assertTrue(sizeBeforeDeleting > sizeAfterDeleting);
        assertEquals(sizeBeforeDeleting - 1, sizeAfterDeleting);
    }
}
