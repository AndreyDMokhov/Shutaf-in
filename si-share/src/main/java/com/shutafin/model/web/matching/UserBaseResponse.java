package com.shutafin.model.web.matching;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserBaseResponse {

    private Long userId;
    private String firstName;
    private String lastName;
    private String profileImage;
}
