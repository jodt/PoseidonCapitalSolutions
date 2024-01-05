package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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

@WebMvcTest(controllers = TradeController.class)
@WithMockUser("john@test.com")
class TradeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TradeService tradeService;

    private Trade trade;

    @BeforeEach
    void init() {
        trade = Trade.builder()
                .account("account")
                .type("type")
                .buyQuantity(10d)
                .build();
    }

    @Test
    @DisplayName("Should display the trade list")
    void shouldDisplayTradeListPage() throws Exception {

        when(this.tradeService.findAllTrades()).thenReturn(List.of(trade));

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("isAdmin", false))
                .andExpect(model().attribute("trades", hasSize(1)))
                .andExpect(model().attribute("trades", allOf(hasItem(trade))))
                .andExpect(view().name("trade/list"));

        verify(tradeService).findAllTrades();
    }

    @Test
    @DisplayName("Should display the add trade form")
    void shouldDisplayAddTradeForm() throws Exception {

        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    @DisplayName("Should save the trade and redirect to the trade listing page")
    void shouldValidateAndSaveATrade() throws Exception {

        mockMvc.perform(post("/trade/validate")
                        .with(csrf())
                        .flashAttr("trade", trade))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    @DisplayName("Should not save the trade -> trade has error fields")
    void shouldNotValidateAndSaveATrade() throws Exception {

        mockMvc.perform(post("/trade/validate")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeHasFieldErrors("trade", "account", "type", "buyQuantity"));
    }

    @Test
    @DisplayName("Should display the trade update form with the trade to be updated")
    void shouldShowUpdateForm() throws Exception {

        when(this.tradeService.findTradeById(1)).thenReturn(trade);

        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attribute("trade", trade));

        verify(tradeService).findTradeById(1);
    }

    @Test
    @DisplayName("Should update the trade and redirect to the trade listing page")
    void shouldUpdateTrade() throws Exception {

        mockMvc.perform(post("/trade/update/1")
                        .with(csrf())
                        .flashAttr("trade", trade))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    @DisplayName("Should not update the trade -> trade has error fields")
    void shouldNotUpdateTrade() throws Exception {

        mockMvc.perform(post("/trade/update/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeHasFieldErrors("trade", "account", "type", "buyQuantity"));
    }

    @Test
    @DisplayName("Should delete the trade and redirect to the trade listing page")
    void shouldDeleteTrade() throws Exception {

        doNothing().when(this.tradeService).deleteTradeById(1);

        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(tradeService).deleteTradeById(1);
    }

}