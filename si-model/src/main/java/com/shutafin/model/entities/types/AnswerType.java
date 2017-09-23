package com.shutafin.model.entities.types;

/**
 * Created by evgeny on 9/22/2017.
 */
public enum AnswerType implements IdentifiableType<Byte> {
    STANDARD((byte)1),
    GENDER((byte)2),
    CITY((byte)3);

    private Byte id;

    AnswerType(Byte id){
        this.id = id;
    }

    @Override
    public Byte getCode() {
        return id;
    }
}
