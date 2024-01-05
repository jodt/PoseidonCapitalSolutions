package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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

@WebMvcTest(controllers = CurveController.class)
@WithMockUser("john@test.com")
class CurveControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CurvePointService curvePointService;

    private CurvePoint curvePoint;

    @BeforeEach
    void init() {
        curvePoint = CurvePoint.builder()
                .term(5d)
                .value(5d)
                .build();
    }

    @Test
    @DisplayName("Should display the list of curve points")
    void shouldDisplayCurveListPage() throws Exception {

        when(this.curvePointService.findAllCurvePoints()).thenReturn(List.of(curvePoint));

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("isAdmin", false))
                .andExpect(model().attribute("curvePoints", hasSize(1)))
                .andExpect(model().attribute("curvePoints", allOf(hasItem(curvePoint))))
                .andExpect(view().name("curvePoint/list"));

        verify(curvePointService).findAllCurvePoints();
    }

    @Test
    @DisplayName("Should display the add curve point form")
    void shouldDisplayAddCurvePointForm() throws Exception {

        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @DisplayName("should save the curve point and redirect to the curve point listing page")
    void shouldValidateAndSaveACurvePoint() throws Exception {

        mockMvc.perform(post("/curvePoint/validate")
                        .with(csrf())
                        .flashAttr("curvePoint", curvePoint))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    @DisplayName("should not save the curve point -> curve point has error fields")
    void shouldNotValidateAndSaveACurvePoint() throws Exception {

        mockMvc.perform(post("/curvePoint/validate")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "term", "value"));
    }

    @Test
    @DisplayName("should display the curve point update form with the curve point to be updated")
    void shouldShowUpdateForm() throws Exception {

        when(this.curvePointService.findCurvePointById(1)).thenReturn(curvePoint);

        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attribute("curvePoint", curvePoint));

        verify(curvePointService).findCurvePointById(1);
    }

    @Test
    @DisplayName("should update the curve point and redirect to the curve point listing page")
    void updateCurvePoint() throws Exception {

        mockMvc.perform(post("/curvePoint/update/1")
                        .with(csrf())
                        .flashAttr("curvePoint", curvePoint))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    @DisplayName("should not update the curve point -> curve point has error fields")
    void shouldNotUpdateCurvePoint() throws Exception {

        mockMvc.perform(post("/curvePoint/update/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "term", "value"));
    }

    @Test
    @DisplayName("should delete the curve point and redirect to the curve point listing page")
    void deleteCurvePoint() throws Exception {

        doNothing().when(this.curvePointService).deleteCurvePointById(1);

        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService).deleteCurvePointById(1);
    }

}