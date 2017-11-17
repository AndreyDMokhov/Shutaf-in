package com.shutafin.model.web.user;

import com.shutafin.annotations.LimitSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserImageWeb {

    private Long id;

    @LimitSize
    private String image;

    private Long createdDate;

}
