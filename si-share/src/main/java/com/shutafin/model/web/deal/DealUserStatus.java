package com.shutafin.model.web.deal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.shutafin.model.types.IdentifiableType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DealUserStatus implements IdentifiableType<Integer> {
    PENDING(1),
    ACTIVE(2),
    LEAVED(3),
    REMOVED(4);

    private Integer id;


    @JsonValue
    public Integer getCode() {
        return id;
    }

    @JsonCreator
    public static DealUserStatus getById(Integer id) {
        if (id == null) {
            return null;
        }

        for (DealUserStatus dealUserStatus : values()) {
            if (dealUserStatus.getCode().equals(id)) {
                return dealUserStatus;
            }
        }

        throw new IllegalArgumentException(String.format("Deal user status with ID %d does not exist", id));
    }
}
