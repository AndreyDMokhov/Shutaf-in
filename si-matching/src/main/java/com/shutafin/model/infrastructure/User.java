package com.shutafin.model.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    public User (Long userId){
        this.userId=userId;
    }
    public User (Long userId, Integer langueageId){
        this.userId=userId;
        this.languageId=langueageId;
    }

    Long userId;
    Integer languageId;
    Integer genderId;
    Integer cityId;
}
