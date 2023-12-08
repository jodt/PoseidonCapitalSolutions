package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exeception.ResourceNotFoundException;

import java.util.List;

public interface RuleNameService {

    List<RuleName> findAllRuleNames();

    RuleName saveRuleName(RuleName ruleName);

    void deleteRuleNameById(Integer id);


    RuleName findRuleNameById(Integer id) throws ResourceNotFoundException;

}
