package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exeception.ResourceNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TradeServiceImpl implements TradeService{

    private final TradeRepository tradeRepository;

    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public List<Trade> findAllTrades() {
        log.info("Try to retrieve all trades");
        return this.tradeRepository.findAll();
    }

    @Override
    public Trade saveTrade(Trade trade) {
        log.info("Try to add or update a trade");
        return this.tradeRepository.save(trade);
    }

    @Override
    public void deleteTradeById(Integer id) {
        log.info("Try to delete trade with id : {}", id);
        this.tradeRepository.deleteById(id);
    }

    @Override
    public Trade findTradeById(Integer id) throws ResourceNotFoundException {
        log.info("Try to find trade with id {}", id);
        return this.tradeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Trade with id " + id + " not found"));
    }

}
