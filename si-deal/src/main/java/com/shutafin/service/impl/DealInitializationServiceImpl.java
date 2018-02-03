package com.shutafin.service.impl;

import com.shutafin.model.web.deal.*;
import com.shutafin.service.DealInitializationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class DealInitializationServiceImpl implements DealInitializationService {
    @Override
    public Map<Integer, String> getDealStatuses() {
        return Arrays.stream(DealStatus.values())
                .collect(Collectors.toMap(DealStatus::getCode, Enum::name));
    }

    @Override
    public Map<Integer, String> getDealUserPermissions() {
        return Arrays.stream(DealUserPermissionType.values())
                .collect(Collectors.toMap(DealUserPermissionType::getCode, Enum::name));
    }

    @Override
    public Map<Integer, String> getDealUserStatuses() {
        return Arrays.stream(DealUserStatus.values())
                .collect(Collectors.toMap(DealUserStatus::getCode,
                        Enum::name));
    }

    @Override
    public Map<Integer, String> getDocumentTypes() {
        return Arrays.stream(DocumentType.values())
                .collect(Collectors.toMap(DocumentType::getCode,
                        Enum::name));
    }

    @Override
    public Map<Integer, String> getPermissionTypes() {
        return Arrays.stream(PermissionType.values())
                .collect(Collectors.toMap(PermissionType::getCode,
                        Enum::name));
    }
}
