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

    /**
     * Get a bid
     *
     * @param id of the requested bid
     * @return bid found by id
     */
    BidListDto getBidListById(Integer id);

    /**
     * Update a bid
     *
     * @param id         bid id to update
     * @param bidListDto bid to update
     * @return bid updated
     */
    BidList updateBidList(Integer id, BidListDto bidListDto);

    /**
     * Delete a bid by Id
     *
     * @param id ID of bid to delete
     */
    void deleteBidList(Integer id);

}
