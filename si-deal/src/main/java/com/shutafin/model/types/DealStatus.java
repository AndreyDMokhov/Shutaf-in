package com.shutafin.model.types;


public enum DealStatus implements IdentifiableType<Integer> {
    INITIATED(1),
    ACTIVE(2),
    ARCHIVE(3);

    private Integer id;

    DealStatus(Integer id) {
        this.id = id;
    }

    public Integer getCode() {
        return id;
    }

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
