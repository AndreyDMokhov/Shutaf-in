package com.shutafin.model.web.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shutafin.annotations.annotations.LimitSize;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountUserImageWeb {

    private Long id;

    @LimitSize
    private String image;

    private Long createdDate;

    private String firstName;

    private String lastName;

    private Long userId;

}
