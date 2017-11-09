package com.shutafin.repository.common.impl;

import com.shutafin.repository.common.VarietyExamKeyRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class VarietyExamKeyRepositoryImpl implements VarietyExamKeyRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<String> getKeysForMatch(String examKeyRegExp) {
        Set<String> res = new HashSet<>();

        List<String> keys = entityManager
                .createQuery("SELECT vek.userExamKey FROM VarietyExamKey vek")
                .getResultList();

        for (String key : keys) {
            if (examKeyRegExp.contains("\\d+")){
                if (examKeyRegExp.replaceAll("\\\\d\\+", "0").matches(key)
                        || key.replaceAll("\\\\d\\+", "0").matches(examKeyRegExp)){
                    res.add(key);
                }
            } else {
                if (examKeyRegExp.matches(key)){
                    res.add(key);
                }
            }
        }

        return new ArrayList<>(res);
    }
}
