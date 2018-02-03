package com.shutafin.service;

import java.util.Map;

public interface DealInitializationService {

    Map<Integer, String> getDealStatuses();
    Map<Integer, String> getDealUserPermissions();
    Map<Integer, String> getDealUserStatuses();
    Map<Integer, String> getDocumentTypes();
    Map<Integer, String> getPermissionTypes();

}
