package com.nnk.springboot.service;

import com.nnk.springboot.config.DataSourceTest;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Dto.CurvePointDto;
import com.nnk.springboot.repositories.CurvePointRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceImplTest {

    @Mock
    CurvePointRepository curvePointRepository;
    @InjectMocks
    CurvePointServiceImpl curvePointService;
    @InjectMocks
    DataSourceTest dataSourceTest;

    @BeforeEach
    void init() {
        dataSourceTest.clearCurvePointListMocked();
        dataSourceTest.createCurvePointListMocked();
    }

    @Test
    @DisplayName("Create new curve point")
    void createCurvePointTest() {

        //GIVEN
        CurvePointDto curvePointDto = CurvePointDto.builder().id(4).curveId(4).term(4d).value(4d).build();

        //WHEN
        curvePointService.createCurvePoint(curvePointDto);

        //THEN
        Mockito.verify(curvePointRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Get all curve points")
    void getAllCurvePointsTest() {

        //GIVEN
        List<CurvePoint> curvePointList = dataSourceTest.getCurvePointListMocked();
        Mockito.when(curvePointRepository.findAllByOrderByIdDesc()).thenReturn(curvePointList);

        //THEN
        List<CurvePointDto> curvePointDtoList = curvePointService.getAllCurvePoints();

        //WHEN
        assertEquals(curvePointList.size(), curvePointDtoList.size());
        assertEquals(curvePointList.get(0).getCurveId(), curvePointDtoList.get(0).getCurveId());
    }

    @Test
    @DisplayName("Get existing curve point by id")
    void getExistingCurvePintByIdTest() throws NotFoundException {

        //GIVEN
        Mockito.when(curvePointRepository.existsById(1)).thenReturn(true);
        CurvePoint curvePoint = dataSourceTest.getCurvePointListMocked().get(0);
        Mockito.when(curvePointRepository.findCurvePointById(1)).thenReturn(curvePoint);

        //THEN
        CurvePointDto curvePointDto = curvePointService.getCurvePointById(1);

        //WHEN
        assertEquals(curvePoint.getCurveId(), curvePointDto.getCurveId());
    }

    @Test
    @DisplayName("Get unknown curve point by id throw NotFoundException")
    void getUnknownCurvePointByIdTest() {

        //GIVEN
        Mockito.when(curvePointRepository.existsById(5)).thenReturn(false);

        //THEN

        //WHEN
        assertThrows(NotFoundException.class, () -> curvePointService.getCurvePointById(5));
    }

    @Test
    @DisplayName("Update an existing curve point")
    void updateExistingCurvePointTest() throws NotFoundException {

        //GIVEN
        Mockito.when(curvePointRepository.existsById(1)).thenReturn(true);
        CurvePoint curvePointToUpdate = dataSourceTest.getCurvePointListMocked().get(0);
        Mockito.when(curvePointRepository.findCurvePointById(1)).thenReturn(curvePointToUpdate);
        CurvePointDto curvePointDto = CurvePointDto.builder().id(1).curveId(10).term(10d).value(10d).build();

        //THEN
        curvePointService.updateCurvePoint(1, curvePointDto);

        //WHEN
        Mockito.verify(curvePointRepository, Mockito.times(1)).save(curvePointToUpdate);
        assertEquals(10, curvePointToUpdate.getCurveId());
    }

    @Test
    @DisplayName("Try to update an unknown curve point throw NotFoundException")
    void updateUnknownCurvePointTest() {

        //GIVEN
        Mockito.when(curvePointRepository.existsById(5)).thenReturn(false);
        CurvePointDto curvePointDto = CurvePointDto.builder().id(10).curveId(10).term(10d).value(10d).build();

        //THEN

        //WHEN
        assertThrows(NotFoundException.class, () -> curvePointService.updateCurvePoint(5, curvePointDto));
    }

    @Test
    @DisplayName("Delete a curve point")
    void deleteCurvePointTest() {

        //GIVEN

        //WHEN
        curvePointService.deleteCurvePoint(1);

        //THEN
        Mockito.verify(curvePointRepository, Mockito.times(1)).deleteById(any());
    }
}
