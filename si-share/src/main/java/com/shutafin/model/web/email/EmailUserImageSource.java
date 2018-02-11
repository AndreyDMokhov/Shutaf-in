package com.shutafin.model.web.email;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailUserImageSource {

    private Long userId;
    private String firstName;
    private String lastName;
    private byte[] imageSource;
}
