package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exeception.ResourceNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
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
class BidListServiceImplTest {
    @Mock
    BidListRepository bidListRepository;

    @InjectMocks
    BidListServiceImpl bidListService;

    BidList bidList;

    @BeforeEach
    void init(){
        bidList = BidList.builder()
                .account("test account")
                .bidQuantity(10d)
                .type("type test")
                .build();
    }

    @Test
    @DisplayName("Should get all bids")
    void shouldGetAllBids() {
        when(this.bidListRepository.findAll()).thenReturn(List.of(bidList));

        List<BidList> result = this.bidListService.getAllBid();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bidList, result.get(0));

        verify(bidListRepository).findAll();
    }

    @Test
    @DisplayName("Should save/update a bid")
    void shouldSaveBid() {

        BidList bidListSaved = bidList;
        bidListSaved.setId(5);

        when(this.bidListRepository.save(bidList)).thenReturn(bidListSaved);

        BidList result = this.bidListService.saveBid(bidList);

        assertNotNull(result);
        assertEquals(bidListSaved, result);

        verify(bidListRepository).save(bidList);
    }

    @Test
    @DisplayName("Should delete a bid by its id")
    void shouldDeleteBidById() {

        doNothing().when(this.bidListRepository).deleteById(10);

        this.bidListService.deleteBidById(10);

        verify(bidListRepository).deleteById(10);
    }

    @Test
    @DisplayName("Should find a bid by its id")
    void shouldFindById() throws ResourceNotFoundException {
        BidList bidListSaved = bidList;
        bidListSaved.setId(5);

        when(this.bidListRepository.findById(5)).thenReturn(Optional.of(bidListSaved));

       BidList result = this.bidListService.findById(5);

       assertNotNull(result);
       assertEquals(bidListSaved, result);

       verify(bidListRepository).findById(5);
    }

    @Test
    @DisplayName("Should not find a bid by its id -> ResourceNotFound Exception")
    void shouldNotFindById() {
        BidList bidListSaved = bidList;
        bidListSaved.setId(5);

        when(this.bidListRepository.findById(10)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()-> this.bidListService.findById(10));

        verify(bidListRepository).findById(10);
    }

}