package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

    /**
     * Find a list of curve points sorted by id desc
     *
     * @return a list of all curve points sorted by id desc
     */
    List<CurvePoint> findAllByOrderByIdDesc();

    /**
     * Find a curve point
     *
     * @param id of the requested curve point
     * @return curve point found by id
     */
    CurvePoint findCurvePointById(Integer id);

}
