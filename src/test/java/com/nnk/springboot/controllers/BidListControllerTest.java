package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BidListController.class)
@WithMockUser("john@test.com")
class BidListControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BidListService bidListService;

    private BidList bidList;

    @BeforeEach
    void init() {
        bidList = BidList.builder()
                .account("test account")
                .type("test type")
                .bidQuantity(10d)
                .build();
    }

    @Test
    @DisplayName("Should display the bid list")
    void shouldDisplayBidListPage() throws Exception {

        when(this.bidListService.getAllBid()).thenReturn(List.of(bidList));

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("isAdmin", false))
                .andExpect(model().attribute("bidLists", hasSize(1)))
                .andExpect(model().attribute("bidLists", allOf(hasItem(bidList))))
                .andExpect(view().name("bidList/list"));

        verify(bidListService).getAllBid();
    }

    @Test
    @DisplayName("Should display the add bid form")
    void shouldDisplayAddBidForm() throws Exception {

        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @DisplayName("Should save the bid and redirect to the bid listing page")
    void shouldValidateAndSaveABid() throws Exception {

        mockMvc.perform(post("/bidList/validate")
                        .with(csrf())
                        .flashAttr("bidList", bidList))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    @DisplayName("Should not save the bid  -> bid has error fields")
    void shouldNotValidateAndSaveABidFormError() throws Exception {

        mockMvc.perform(post("/bidList/validate")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeHasFieldErrors("bidList", "account", "type", "bidQuantity"));
    }

    @Test
    @DisplayName("Should display the bid update form with the bid to be updated")
    void shouldShowUpdateForm() throws Exception {

        when(this.bidListService.findById(1)).thenReturn(bidList);

        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attribute("bidList", bidList));

        verify(bidListService).findById(1);
    }

    @Test
    @DisplayName("Should update the bid and redirect to the bid listing page")
    void shouldUpdateBid() throws Exception {

        mockMvc.perform(post("/bidList/update/1")
                        .with(csrf())
                        .flashAttr("bidList", bidList))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    @DisplayName("Should not update the bid -> bid has error fields")
    void shouldNotUpdateBid() throws Exception {

        mockMvc.perform(post("/bidList/update/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeHasFieldErrors("bidList", "account", "type", "bidQuantity"));
    }

    @Test
    @DisplayName("Should delete the bid and redirect to the bid listing page")
    void shouldDeleteBid() throws Exception {

        doNothing().when(this.bidListService).deleteBidById(1);

        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(bidListService).deleteBidById(1);
    }

}