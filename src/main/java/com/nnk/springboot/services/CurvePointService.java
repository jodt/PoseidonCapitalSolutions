package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exeception.ResourceNotFoundException;

import java.util.List;

public interface CurvePointService {

    List<CurvePoint> findAllCurvePoints();

    CurvePoint saveCurvePoint(CurvePoint curvePoint);

    void deleteCurvePointById(Integer id);

    CurvePoint findCurvePointById(Integer id) throws ResourceNotFoundException;

}
