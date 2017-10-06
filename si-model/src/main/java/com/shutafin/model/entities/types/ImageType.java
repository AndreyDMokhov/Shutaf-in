package com.shutafin.model.entities.types;

public enum ImageType implements IdentifiableType<Integer> {
    PROFILE_IMAGE(1);

    private Integer id;

    ImageType(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getCode() {
        return id;
    }
}
