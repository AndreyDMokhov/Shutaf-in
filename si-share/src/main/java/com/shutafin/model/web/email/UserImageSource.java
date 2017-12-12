package com.shutafin.model.web.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserImageSource {

    private Long userId;
    private String firstName;
    private String lastName;
    private byte[] imageSource;

}
