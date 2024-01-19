package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exeception.ResourceNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurvePointServiceImplTest {

    @Mock
    CurvePointRepository curvePointRepository;

    @InjectMocks
    CurvePointServiceImpl curvePointService;

    private CurvePoint curvePoint;

    @BeforeEach
    void init(){

        curvePoint = CurvePoint.builder()
                .term(10d)
                .value(10d)
                .build();

    }

    @Test
    @DisplayName("Should find all curve points")
    void shouldFindAllCurvePoints() {

        when(this.curvePointRepository.findAll()).thenReturn(List.of(curvePoint));

        List<CurvePoint> result = this.curvePointService.findAllCurvePoints();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(curvePoint, result.get(0));

        verify(curvePointRepository).findAll();
    }

    @Test
    @DisplayName("Should save/update a curve point")
    void shouldSaveCurvePoint() {

        CurvePoint curvePointSaved = curvePoint;
        curvePointSaved.setId(5);

        when(this.curvePointRepository.save(curvePoint)).thenReturn(curvePointSaved);

        CurvePoint result = this.curvePointService.saveCurvePoint(curvePoint);

        assertNotNull(result);
        assertEquals(curvePointSaved,result);
    }

    @Test
    @DisplayName("Should delete a curve point")
    void shouldDeleteCurvePointById() {

        doNothing().when(this.curvePointRepository).deleteById(10);

        this.curvePointService.deleteCurvePointById(10);

        verify(curvePointRepository).deleteById(10);
    }

    @Test
    @DisplayName("Should find a curve point by its id")
    void shouldFindCurvePointById() throws ResourceNotFoundException {

        CurvePoint curvePointSaved = curvePoint;
        curvePointSaved.setId(5);

        when(this.curvePointRepository.findById(5)).thenReturn(Optional.of(curvePointSaved));

        CurvePoint result = this.curvePointService.findCurvePointById(5);

        assertNotNull(result);
        assertEquals(curvePointSaved,result);

        verify(curvePointRepository).findById(5);

    }

    @Test
    @DisplayName("Should not find a curve point by its id -> ResourceNotFoundException")
    void shouldNotFindCurvePointById() {
        CurvePoint curvePointSaved = curvePoint;
        curvePointSaved.setId(5);

        when(this.curvePointRepository.findById(10)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> this.curvePointService.findCurvePointById(10));

        verify(curvePointRepository).findById(10);

    }

}