package com.shutafin.model.web.deal;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.shutafin.model.types.IdentifiableType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DealStatus implements IdentifiableType<Integer> {
    INITIATED(1),
    ACTIVE(2),
    ARCHIVE(3);

    private Integer id;

    @JsonValue
    public Integer getCode() {
        return id;
    }

    @JsonCreator
    public static DealStatus getById(Integer id) {
        if (id == null) {
            return null;
        }

        for (DealStatus dealStatus : values()) {
            if (dealStatus.getCode().equals(id)) {
                return dealStatus;
            }
        }

        throw new IllegalArgumentException(String.format("Deal status with ID %d does not exist", id));
    }
}
