package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exeception.ResourceNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CurvePointServiceImpl implements CurvePointService{

    private final CurvePointRepository curvePointRepository;

    public CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    @Override
    public List<CurvePoint> findAllCurvePoints() {
        log.info("Try to retrieve all curve points");
        return this.curvePointRepository.findAll();
    }

    @Override
    public CurvePoint saveCurvePoint(CurvePoint curvePoint) {
        log.info("Try to add or update a new curve point");
        return this.curvePointRepository.save(curvePoint);
    }

    @Override
    public void deleteCurvePointById(Integer id) {
        log.info("Try to delete curve point with id : {}", id);
        this.curvePointRepository.deleteById(id);
    }

    @Override
    public CurvePoint findCurvePointById(Integer id) throws ResourceNotFoundException {
        log.info("Try to find curve point with id {}", id);
        return this.curvePointRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Curve point with id " + id + " not found"));
    }

}
