package com.shutafin.model.web.deal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.shutafin.model.types.IdentifiableType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DealUserPermissionType implements IdentifiableType<Integer> {
    CREATE(1),
    READ_ONLY(2),
    NO_READ(3);

    private Integer id;


    @JsonValue
    public Integer getCode() {
        return id;
    }

    @JsonCreator
    public static DealUserPermissionType getById(Integer id) {
        if (id == null) {
            return null;
        }

        for (DealUserPermissionType dealUserPermissionType : values()) {
            if (dealUserPermissionType.getCode().equals(id)) {
                return dealUserPermissionType;
            }
        }

        throw new IllegalArgumentException(String.format("Deal user permission with ID %d does not exist", id));
    }
}
