package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {

    /**
     * Find a bid by Id
     *
     * @param BidListId bid id sought
     * @return the bid found
     */
    BidList findBidListById(Integer BidListId);

}
