package com.shutafin.model.entities.types;

@Deprecated
public enum PermissionType implements IdentifiableType<Integer> {
    PUBLIC(1),
    PRIVATE(2),
    GROUP(3);

    private Integer id;

    PermissionType(Integer id) {
        this.id = id;
    }

    public Integer getCode() {
        return id;
    }

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
