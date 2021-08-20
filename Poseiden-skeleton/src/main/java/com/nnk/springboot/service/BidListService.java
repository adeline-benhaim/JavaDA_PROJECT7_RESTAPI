package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Dto.BidListDto;

public interface BidListService {

    /**
     * Create a new bid
     *
     * @param bidListDto new bid to save
     * @return bid saved
     */
    BidList createBidList(BidListDto bidListDto);

}
