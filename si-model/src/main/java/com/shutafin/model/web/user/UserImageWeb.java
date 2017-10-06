package com.shutafin.model.web.user;

import com.shutafin.annotations.LimitSize;
import com.shutafin.model.entities.types.ImageType;
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
    @LimitSize(value = ImageType.PROFILE_IMAGE)
    private String image;

    private Long createdDate;

}
