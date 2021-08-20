package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Dto.BidListDto;
import com.nnk.springboot.repositories.BidListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidListServiceImpl implements BidListService {
    private static final Logger logger = LoggerFactory.getLogger(BidListServiceImpl.class);

    @Autowired
    BidListRepository bidListRepository;

    /**
     * Create a new bid
     *
     * @param bidListDto new bid to save
     * @return bid saved
     */
    @Override
    public BidList createBidList(BidListDto bidListDto) {
        logger.info("Create a new bidList");
        BidList newBidList = BidList.builder()
                .BidListId(bidListDto.getBidListId())
                .account(bidListDto.getAccount())
                .type(bidListDto.getType())
                .bidQuantity(bidListDto.getBidQuantity())
                .build();
        return bidListRepository.save(newBidList);
    }
}
