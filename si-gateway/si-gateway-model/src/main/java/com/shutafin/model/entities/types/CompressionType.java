package com.shutafin.model.entities.types;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CompressionType implements IdentifiableType<Integer> {

    NO_COMPRESSION(0, null, null),
    COMPRESSION_RATE_0_7(1, 256, 0.7f);

    private Integer id;
    private Integer compressSize;
    private Float compressionQuality;

    @Override
    public Integer getCode() {
        return id;
    }

    public Integer getCompressSize() {
        return compressSize;
    }

    public Float getCompressionQuality() {
        return compressionQuality;
    }

    public static CompressionType getById(Integer id) {
        if (id == null) {
            return null;
        }

        for (CompressionType compressionType : values()) {
            if (compressionType.getCode().equals(id)) {
                return compressionType;
            }
        }

        throw new IllegalArgumentException(String.format("Compression type with ID %d does not exist", id));
    }
}
