package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exeception.ResourceNotFoundException;

import java.util.List;

public interface BidListService {

    List<BidList> getAllBid();

    BidList saveBid(BidList bid);

    void deleteBidById(Integer id);

    BidList findById(Integer id) throws ResourceNotFoundException;

}
