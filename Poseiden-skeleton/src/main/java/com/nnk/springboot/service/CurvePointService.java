package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Dto.CurvePointDto;

public interface CurvePointService {

    /**
     * Create a new curve point
     *
     * @param curvePointDto new curve point to save
     * @return curve point saved
     */
    CurvePoint createCurvePoint(CurvePointDto curvePointDto);
}
