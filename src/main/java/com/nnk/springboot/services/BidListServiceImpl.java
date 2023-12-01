package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exeception.ResourceNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BidListServiceImpl implements BidListService {

    private final BidListRepository bidListRepository;

    public BidListServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    @Override
    public List<BidList> getAllBid() {
        log.info("Try to retrieve all bids");
        return this.bidListRepository.findAll();
    }

    @Override
    public BidList saveBid(BidList bid) {
        log.info("Try to add or update a new bid");
        return this.bidListRepository.save(bid);
    }

    @Override
    public void deleteBidById(Integer id) {
        log.info("Try to delete bid with id : {}", id);
        this.bidListRepository.deleteById(id);
    }

    @Override
    public BidList findById(Integer id) throws ResourceNotFoundException {
        log.info("Try to find bid with id {}", id);
        return this.bidListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bid with id :" + id + "not found"));
    }

}
