package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exeception.ResourceNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
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
class RuleNameServiceImplTest {

    @Mock
    RuleNameRepository ruleNameRepository;

    @InjectMocks
    RuleNameServiceImpl ruleNameService;

    private RuleName ruleName;

    @BeforeEach
    void init(){
        ruleName = RuleName.builder()
                .name("rule test")
                .description("description test")
                .json("json test")
                .sqlPart("sqlPart test")
                .sqlStr("sqlStr test")
                .build();
    }

    @Test
    @DisplayName("Should find all rulenames")
    void shouldFindAllRuleNames() {

        when(this.ruleNameRepository.findAll()).thenReturn(List.of(ruleName));

        List<RuleName> result = this.ruleNameService.findAllRuleNames();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(ruleName, result.get(0));

        verify(ruleNameRepository).findAll();
    }

    @Test
    @DisplayName("Should save or update a rulename")
    void shouldSaveRuleName() {
        RuleName ruleNameSaved = ruleName;
        ruleNameSaved.setId(5);

        when(this.ruleNameRepository.save(ruleName)).thenReturn(ruleNameSaved);

        RuleName result = this.ruleNameService.saveRuleName(ruleName);

        assertNotNull(result);
        assertEquals(ruleNameSaved, result);

        verify(ruleNameRepository).save(ruleName);

    }

    @Test
    @DisplayName("Should delete a rulename by id")
    void shouldDeleteRuleNameById() {

        doNothing().when(this.ruleNameRepository).deleteById(5);

        this.ruleNameService.deleteRuleNameById(5);

        verify(ruleNameRepository).deleteById(5);
    }

    @Test
    @DisplayName("Should find a rulename by id")
    void shouldFindRuleNameById() throws ResourceNotFoundException {
        RuleName ruleNameSaved = ruleName;
        ruleNameSaved.setId(5);

        when(this.ruleNameRepository.findById(5)).thenReturn(Optional.of(ruleNameSaved));

        RuleName result = this.ruleNameService.findRuleNameById(5);

        assertNotNull(result);
        assertEquals(ruleNameSaved, result);

        verify(this.ruleNameRepository).findById(5);
    }

    @Test
    @DisplayName("Should not find a rulename by id -> ResourceNotFoundException")
    void shouldNotFindRuleNameById() {
        RuleName ruleNameSaved = ruleName;
        ruleNameSaved.setId(5);

        when(this.ruleNameRepository.findById(10)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> this.ruleNameService.findRuleNameById(10));

        verify(this.ruleNameRepository).findById(10);

    }

}