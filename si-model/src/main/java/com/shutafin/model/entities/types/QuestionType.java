package com.shutafin.model.entities.types;

/**
 * Created by evgeny on 9/22/2017.
 */
public enum QuestionType implements IdentifiableType<Integer> {
    STANDARD(1),
    GENDER(2),
    CITY(3);

    private Integer id;

    QuestionType(Integer id){
        this.id = id;
    }

    @Override
    public Integer getCode() {
        return id;
    }

    public static QuestionType getById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("QuestionType type ID cannot be null");
        }

        for (QuestionType questionType : values()) {
            if (questionType.getCode().equals(id)) {
                return questionType;
            }
        }

        throw new IllegalArgumentException("QuestionType type with ID [" + id + "] does not exist");
    }
}
