package com.shutafin.model.web.deal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DealInitializationResponse {

    private Map<Integer, String> dealStatuses;
    private Map<Integer, String> dealUserPermissions;
    private Map<Integer, String> dealUserStatuses;
    private Map<Integer, String> documentTypes;
    private Map<Integer, String> permissionTypes;
}
