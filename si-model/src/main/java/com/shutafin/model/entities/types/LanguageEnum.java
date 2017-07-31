package com.shutafin.model.entities.types;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.NUMBER)
public enum LanguageEnum implements IdentifiableType<Integer> {
    ENGLISH(1, "en"),
    RUSSIAN(2, "ru");

    @JsonProperty(value = "languageId")
    private Integer id;

    private String languageCode;

    LanguageEnum(Integer id, String languageCode) {
        this.id = id;
        this.languageCode = languageCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    @Override
    public Integer getCode() {
        return id;
    }

    public static LanguageEnum getById(Integer id) {
        if (id == null) {
            return LanguageEnum.ENGLISH;
        }

        for (LanguageEnum languageEnum : values()) {
            if (languageEnum.getCode().equals(id)) {
                return languageEnum;
            }
        }

        throw new IllegalArgumentException(String.format("LanguageEnum with ID %d does not exist", id));
    }


}
