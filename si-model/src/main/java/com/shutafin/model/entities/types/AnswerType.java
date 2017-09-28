package com.shutafin.model.entities.types;

/**
 * Created by evgeny on 9/22/2017.
 */
public enum AnswerType implements IdentifiableType<Integer> {
    STANDARD(1),
    GENDER(2),
    CITY(3);

    private Integer id;

    AnswerType(Integer id){
        this.id = id;
    }

    @Override
    public Integer getCode() {
        return id;
    }
}
