package com.shutafin.repository.common;

import java.util.List;

@Deprecated
public interface VarietyExamKeyRepositoryCustom {

    List<String> getKeysForMatch(String examKey);
}
