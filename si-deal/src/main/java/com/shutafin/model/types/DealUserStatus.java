package com.shutafin.model.types;

public enum DealUserStatus implements IdentifiableType<Integer> {
    PENDING(1),
    ACTIVE(2),
    LEAVED(3),
    REMOVED(4);

    private Integer id;

    DealUserStatus(Integer id) {
        this.id = id;
    }

    public Integer getCode() {
        return id;
    }

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
