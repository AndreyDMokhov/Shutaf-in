package com.shutafin.model.web.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserBaseResponse {

    private Long userId;
    private String firstName;
    private String lastName;
    private String profileImage;
}
