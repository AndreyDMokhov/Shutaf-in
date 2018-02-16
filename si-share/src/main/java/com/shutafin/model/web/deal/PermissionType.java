package com.shutafin.model.web.deal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.shutafin.model.types.IdentifiableType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PermissionType implements IdentifiableType<Integer> {
    PUBLIC(1),
    PRIVATE(2),
    DEAL(3);

    private Integer id;


    @JsonValue
    public Integer getCode() {
        return id;
    }

    @JsonCreator
    public static PermissionType getById(Integer id) {
        if (id == null) {
            return null;
        }

        for (PermissionType permissionType : values()) {
            if (permissionType.getCode().equals(id)) {
                return permissionType;
            }
        }

        throw new IllegalArgumentException(String.format("Access level with ID %d does not exist", id));
    }
}
