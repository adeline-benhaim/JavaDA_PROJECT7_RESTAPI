package com.nnk.springboot.service;

import com.nnk.springboot.mapper.MapperDto;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Dto.CurvePointDto;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    @Override
    public CurvePoint createCurvePoint(CurvePointDto curvePointDto) {
        logger.info("Create a new curvePoint");
        CurvePoint newCurvePoint = CurvePoint.builder()
                .curveId(curvePointDto.getCurveId())
                .term(curvePointDto.getTerm())
                .value(curvePointDto.getValue())
                .build();
        return curvePointRepository.save(newCurvePoint);
    }

    /**
     * Get all curve points
     *
     * @return a list of all curve points
     */
    @Override
    public List<CurvePointDto> getAllCurvePoints() {
        logger.info("Get all curvePoints");
        List<CurvePoint> curvePointList = curvePointRepository.findAllByOrderByIdDesc();
        return curvePointList
                .stream().map(MapperDto::convertToCurvePointDto)
                .collect(Collectors.toList());
    }

    /**
     * Get a curve point
     *
     * @param id of the requested curve point
     * @return curve point found by id
     */
    @Override
    public CurvePointDto getCurvePointById(Integer id) throws NotFoundException {
        logger.info("Get a curvePoint by ID");
        if (!curvePointRepository.existsById(id)) {
            logger.error("Unable to get curvePoint by id : " + id + " because doesn't exist");
            throw new NotFoundException("curvePoint id doesn't exist");
        }
        CurvePoint curvePoint = curvePointRepository.findCurvePointById(id);
        return MapperDto.convertToCurvePointDto(curvePoint);
    }

    /**
     * Update a curve point
     *
     * @param id curve point id to update
     * @param curvePointDto curve point to update
     * @return curve point updated
     */
    @Override
    @Transactional
    public CurvePoint updateCurvePoint(Integer id, CurvePointDto curvePointDto) throws NotFoundException {
        logger.info("Get curve point by id");
        if (!curvePointRepository.existsById(id)) {
            logger.error("Unable to get curvePoint by id : " + id + " because doesn't exist");
            throw new NotFoundException("curvePoint id doesn't exist");
        }
        CurvePoint curvePointToUpdate = curvePointRepository.findCurvePointById(id);
        curvePointToUpdate.setCurveId(curvePointDto.getCurveId());
        curvePointToUpdate.setTerm(curvePointDto.getTerm());
        curvePointToUpdate.setValue(curvePointDto.getValue());
        logger.info("Curve point updated");
        return curvePointRepository.save(curvePointToUpdate);
    }

    /**
     * Delete a curve point by id
     *
     * @param id ID of curve point to delete
     */
    @Override
    @Transactional
    public void deleteCurvePoint(Integer id) {
        logger.info("Delete curve point : " + id);
        curvePointRepository.deleteById(id);
    }
}
