package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.match.VarietyExamKey;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.common.VarietyExamKeyRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by evgeny on 9/9/2017.
 */
@Repository
public class VarietyExamKeyRepositoryImpl extends AbstractEntityDao<VarietyExamKey> implements VarietyExamKeyRepository {

    @Override
    public VarietyExamKey findUserExamKeyByStr(String key) {
        return (VarietyExamKey) getSession()
                .createQuery("SELECT vek FROM VarietyExamKey vek where vek.userExamKey = :key")
                .setParameter("key", key)
                .setCacheable(true)
                .uniqueResult();
    }

    @Override
    public List<String> getKeysForMatch(String examKeyRegExp) {
        Set<String> res = new HashSet<>();

        List<String> keys = getSession()
                .createQuery("SELECT vek.userExamKey FROM VarietyExamKey vek")
                .list();

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
