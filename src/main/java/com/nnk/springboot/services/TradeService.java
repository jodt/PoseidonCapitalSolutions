package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exeception.ResourceNotFoundException;

import java.util.List;

public interface TradeService {

    List<Trade> findAllTrades();

    Trade saveTrade(Trade trade);

    void deleteTradeById(Integer id);

    Trade findTradeById(Integer id) throws ResourceNotFoundException;

}
