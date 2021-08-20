package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Dto.BidListDto;

import java.util.List;

public interface BidListService {

    /**
     * Create a new bid
     *
     * @param bidListDto new bid to save
     * @return bid saved
     */
    BidList createBidList(BidListDto bidListDto);

    /**
     * Get all bids
     *
     * @return list of bids found
     */
    List<BidListDto> getAllBidList();

}
