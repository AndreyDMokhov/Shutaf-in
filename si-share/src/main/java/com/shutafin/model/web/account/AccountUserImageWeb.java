package com.shutafin.model.web.account;

import com.shutafin.annotations.annotations.LimitSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountUserImageWeb {

    private Long id;

    @LimitSize
    private String image;

    private Long createdDate;

}
