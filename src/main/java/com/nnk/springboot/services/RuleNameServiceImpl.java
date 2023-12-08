package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exeception.ResourceNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RuleNameServiceImpl implements RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    public RuleNameServiceImpl(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    @Override
    public List<RuleName> findAllRuleNames() {
        log.info("Try to retrieve all ruleNames");
        return this.ruleNameRepository.findAll();
    }

    @Override
    public RuleName saveRuleName(RuleName ruleName) {
        log.info("Try to add or update a ruleName");
        return this.ruleNameRepository.save(ruleName);
    }

    @Override
    public void deleteRuleNameById(Integer id) {
        log.info("Try to delete ruleName with id : {}", id);
        this.ruleNameRepository.deleteById(id);
    }

    @Override
    public RuleName findRuleNameById(Integer id) throws ResourceNotFoundException {
        log.info("Try to find ruleName with id {}", id);
        return this.ruleNameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RuleName with id " + id + " not found"));
    }

}
