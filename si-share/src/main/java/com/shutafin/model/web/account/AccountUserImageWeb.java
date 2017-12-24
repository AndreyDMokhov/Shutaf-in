package com.shutafin.model.web.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shutafin.annotations.annotations.LimitSize;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountUserImageWeb {

    private Long id;

    @LimitSize
    private String image;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long createdDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String firstName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String lastName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long userId;

}
