package com.shutafin.model.web.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInitializationData {

    private String firstName;
    private String lastName;
    private Integer languageId;
    private Long userImageId;
    private String userImage;


    public UserInitializationData(String firstName, String lastName, Integer languageId) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.languageId = languageId;
    }


}


