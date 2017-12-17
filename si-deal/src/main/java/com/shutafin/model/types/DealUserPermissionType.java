package com.shutafin.model.types;

public enum DealUserPermissionType implements IdentifiableType<Integer> {
    CREATE(1),
    READ_ONLY(2),
    NO_READ(3);

    private Integer id;

    DealUserPermissionType(Integer id) {
        this.id = id;
    }

    public Integer getCode() {
        return id;
    }

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
