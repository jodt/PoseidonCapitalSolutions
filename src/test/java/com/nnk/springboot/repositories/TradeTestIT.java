package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class TradeTestIT {

    @Autowired
    private TradeRepository tradeRepository;

    @Test
    public void tradeTest() {
        Trade trade = new Trade();
        trade.setAccount("Trade Account");
        trade.setType("Type");
        trade.setBuyQuantity(10d);

        // Save
        trade = tradeRepository.save(trade);
        Assert.assertNotNull(trade.getTradeId());
        Assert.assertTrue(trade.getAccount().equals("Trade Account"));

        // Update
        trade.setAccount("Trade Account Update");
        trade = tradeRepository.save(trade);
        Assert.assertTrue(trade.getAccount().equals("Trade Account Update"));

        // Find
        List<Trade> listResult = tradeRepository.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = trade.getTradeId();
        tradeRepository.delete(trade);
        Optional<Trade> tradeList = tradeRepository.findById(id);
        Assert.assertFalse(tradeList.isPresent());
    }

}
