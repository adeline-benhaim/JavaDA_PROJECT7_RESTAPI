package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Dto.CurvePointDto;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurvePointServiceImpl implements CurvePointService {
    private static final Logger logger = LoggerFactory.getLogger(CurvePointServiceImpl.class);

    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     * Create a new curve point
     *
     * @param curvePointDto new curve point to save
     * @return curve point saved
     */
    public CurvePoint createCurvePoint(CurvePointDto curvePointDto) {
        logger.info("Create a new curvePoint");
        CurvePoint newCurvePoint = CurvePoint.builder()
                .curveId(curvePointDto.getCurveId())
                .term(curvePointDto.getTerm())
                .value(curvePointDto.getValue())
                .build();
        return curvePointRepository.save(newCurvePoint);
    }
}
