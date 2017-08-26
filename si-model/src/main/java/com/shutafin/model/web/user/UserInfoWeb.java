package com.shutafin.model.web.user;


import com.shutafin.model.web.DataResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfoWeb implements DataResponse {

    private int cityId;
    private int genderId;

    private String facebookLink;
    private String profession;
    private String company;
    private String phoneNumber;

}
