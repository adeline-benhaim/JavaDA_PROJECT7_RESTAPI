package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Dto.CurvePointDto;
import javassist.NotFoundException;

import java.util.List;

public interface CurvePointService {

    /**
     * Create a new curve point
     *
     * @param curvePointDto new curve point to save
     * @return curve point saved
     */
    CurvePoint createCurvePoint(CurvePointDto curvePointDto);

    /**
     * Get all curve points
     *
     * @return a list of all curve points
     */
    List<CurvePointDto> getAllCurvePoints();

    /**
     * Get a curve point
     *
     * @param id of the requested curve point
     * @return curve point found by id
     */
    CurvePointDto getCurvePointById(Integer id) throws NotFoundException;

    /**
     * Update a curve point
     *
     * @param id curve point id to update
     * @param curvePointDto curve point to update
     * @return curve point updated
     */
    CurvePoint updateCurvePoint(Integer id, CurvePointDto curvePointDto) throws NotFoundException;

    /**
     * Delete a curve point by id
     *
     * @param id ID of curve point to delete
     */
    void deleteCurvePoint(Integer id);
}
