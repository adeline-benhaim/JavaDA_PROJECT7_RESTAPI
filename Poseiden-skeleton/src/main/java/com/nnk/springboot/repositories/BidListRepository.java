package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {

    /**
     * Find a list of bids sorted by id desc
     *
     * @return a list of all bids sorted by id desc
     */
    List<BidList> findAllByOrderByBidListIdDesc();
}
