package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {

    /**
     * Find a list of rules sorted by id desc
     *
     * @return a list of all rules sorted by id desc
     */
    List<RuleName> findAllByOrderByIdDesc();

    /**
     * Find a rule
     *
     * @param id of the requested rule
     * @return rule found by id
     */
    RuleName findRuleNameById(Integer id);
}
