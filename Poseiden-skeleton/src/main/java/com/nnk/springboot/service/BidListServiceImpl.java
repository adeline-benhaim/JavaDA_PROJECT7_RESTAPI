package com.nnk.springboot.service;

import com.nnk.springboot.mapper.MapperDto;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Dto.BidListDto;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BidListServiceImpl implements BidListService {
    private static final Logger logger = LoggerFactory.getLogger(BidListServiceImpl.class);

    @Autowired
    private BidListRepository bidListRepository;

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
                .account(bidListDto.getAccount())
                .type(bidListDto.getType())
                .bidQuantity(bidListDto.getBidQuantity())
                .build();
        return bidListRepository.save(newBidList);
    }

    /**
     * Get all bids
     *
     * @return list of bids found
     */
    @Override
    public List<BidListDto> getAllBidList() {
        logger.info("Get all bidList");
        List<BidList> bidList = bidListRepository.findAllByOrderByBidListIdDesc();
        return bidList
                .stream().map(MapperDto::convertToBidListDto)
                .collect(Collectors.toList());
    }

    /**
     * Get a bid
     *
     * @param id of the requested bid
     * @return bid found by id
     */
    @Override
    public BidListDto getBidListById(Integer id) {
        logger.info("Get a bidList by ID");
        if (!bidListRepository.existsById(id)) {
            logger.error("Unable to get bid by id : " + id + " because doesn't exist");
            throw new NotFoundException("Bid id doesn't exist");
        }
        BidList bidList = bidListRepository.getById(id);
        return MapperDto.convertToBidListDto(bidList);
    }

    /**
     * Update a bid
     *
     * @param id bid id to update
     * @param bidListDto bid to update
     * @return bid updated
     */
    @Override
    @Transactional
    public BidList updateBidList(Integer id, BidListDto bidListDto) {
        logger.info("Get bidList by id");
        if (!bidListRepository.existsById(id)) {
            logger.error("Unable to get bid by id : " + id + " because doesn't exist");
            throw new NotFoundException("Bid id doesn't exist");
        }
        BidList bidListToUpdate = bidListRepository.getById(id);
        bidListToUpdate.setAccount(bidListDto.getAccount());
        bidListToUpdate.setType(bidListDto.getType());
        bidListToUpdate.setBidQuantity(bidListDto.getBidQuantity());
        logger.info("BidList updated");
        return bidListRepository.save(bidListToUpdate);
    }

    /**
     * Delete a bid by Id
     *
     * @param id ID of bid to delete
     */
    @Override
    @Transactional
    public void deleteBidList(Integer id) {
        logger.info("Delete bidList : " + id);
        bidListRepository.deleteById(id);
    }
}
