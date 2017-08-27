package com.shutafin.model.web.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserImageWeb {

    private Long id;

    @NotBlank
    private String image;

    private Long createdDate;

}
