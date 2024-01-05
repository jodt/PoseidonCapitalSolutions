package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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

@WebMvcTest(controllers = RuleNameController.class)
@WithMockUser("john@test.com")
class RuleNameControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RuleNameService ruleNameService;

    private RuleName ruleName;

    @BeforeEach
    void init() {
        ruleName = RuleName.builder()
                .name("name")
                .description("description")
                .json("json")
                .template("template")
                .sqlStr("sqlStr")
                .sqlPart("sqlPart")
                .build();
    }

    @Test
    @DisplayName("Should display the rule name list")
    void shouldDisplayRuleNameListPage() throws Exception {

        when(this.ruleNameService.findAllRuleNames()).thenReturn(List.of(ruleName));

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("isAdmin", false))
                .andExpect(model().attribute("ruleNames", hasSize(1)))
                .andExpect(model().attribute("ruleNames", allOf(hasItem(ruleName))))
                .andExpect(view().name("ruleName/list"));

        verify(ruleNameService).findAllRuleNames();
    }

    @Test
    @DisplayName("Should display the add rule name form")
    void shouldDisplayAddRuleForm() throws Exception {

        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @DisplayName("Should save the rule name and redirect to the rule name listing page")
    void shouldValidateAndSaveARuleName() throws Exception {

        mockMvc.perform(post("/ruleName/validate")
                        .with(csrf())
                        .flashAttr("ruleName", ruleName))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    @DisplayName("Should not save the rule name -> rule name has error fields")
    void shouldNotValidateAndSaveARuleName() throws Exception {

        mockMvc.perform(post("/ruleName/validate")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeHasFieldErrors("ruleName", "name"));
    }

    @Test
    @DisplayName("Should display the rule name update form with the rule name to be updated")
    void shouldShowUpdateForm() throws Exception {

        when(this.ruleNameService.findRuleNameById(1)).thenReturn(ruleName);

        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attribute("ruleName", ruleName));

        verify(ruleNameService).findRuleNameById(1);
    }

    @Test
    @DisplayName("Should update the rule name and redirect to the rule name listing page")
    void shouldUpdateRuleName() throws Exception {

        mockMvc.perform(post("/ruleName/update/1")
                        .with(csrf())
                        .flashAttr("ruleName", ruleName))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    @DisplayName("Should not update the rule name -> rule name has error fields")
    void shouldNotUpdateRuleName() throws Exception {

        mockMvc.perform(post("/ruleName/update/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeHasFieldErrors("ruleName", "name"));
    }

    @Test
    @DisplayName("Should delete the rule name and redirect to the rule name listing page")
    void deleteRuleName() throws Exception {

        doNothing().when(this.ruleNameService).deleteRuleNameById(1);

        mockMvc.perform(get("/ruleName/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService).deleteRuleNameById(1);
    }

}