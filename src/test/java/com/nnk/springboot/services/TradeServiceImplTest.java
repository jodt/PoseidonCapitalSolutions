package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exeception.ResourceNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
import jakarta.validation.constraints.Min;
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
class TradeServiceImplTest {

    @Mock
    TradeRepository tradeRepository;

    @InjectMocks
    TradeServiceImpl tradeService;

    private Trade trade;

    @BeforeEach
    void init(){
        trade = Trade.builder()
                .account("account test")
                .type("type test")
                .buyQuantity(10d)
                .build();
    }

    @Test
    @DisplayName("Should find all trades")
    void shouldFindAllTrades() {

        when(this.tradeRepository.findAll()).thenReturn(List.of(trade));

        List<Trade> result = this.tradeService.findAllTrades();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(trade, result.get(0));

        verify(tradeRepository).findAll();
    }

    @Test
    @DisplayName("Should save or update a trade")
    void shouldSaveTrade() {
        Trade tradeSaved = trade;
        tradeSaved.setTradeId(5);

        when(this.tradeRepository.save(trade)).thenReturn(tradeSaved);

        Trade result = this.tradeService.saveTrade(trade);

        assertNotNull(result);
        assertEquals(tradeSaved, result);

        verify(tradeRepository).save(trade);
    }

    @Test
    @DisplayName("Should delete a trade by id")
    void deleteTradeById() {

        doNothing().when(this.tradeRepository).deleteById(5);

        this.tradeService.deleteTradeById(5);

        verify(this.tradeRepository).deleteById(5);
    }

    @Test
    @DisplayName("Should find a trade by id")
    void shouldFindTradeById() throws ResourceNotFoundException {
        Trade tradeSaved = trade;
        tradeSaved.setTradeId(5);

        when(this.tradeRepository.findById(5)).thenReturn(Optional.of(trade));

        Trade result = this.tradeService.findTradeById(5);

        assertNotNull(result);
        assertEquals(tradeSaved, result);

        verify(tradeRepository).findById(5);
    }

    @Test
    @DisplayName("Should not find a trade by id -> ResourceNotFoundException")
    void shouldNotFindTradeById() {
        Trade tradeSaved = trade;
        tradeSaved.setTradeId(5);

        when(this.tradeRepository.findById(10)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> this.tradeService.findTradeById(10));

        verify(tradeRepository).findById(10);
    }

}