package com.shutafin.repository.common;

import com.shutafin.model.entities.match.VarietyExamKey;
import com.shutafin.repository.base.PersistentDao;

import java.util.List;

/**
 * Created by evgeny on 9/9/2017.
 */
public interface VarietyExamKeyRepository extends PersistentDao<VarietyExamKey> {
    VarietyExamKey findUserExamKeyByStr(String key);
    List<String> getKeysForMatch(String examKey);
}
