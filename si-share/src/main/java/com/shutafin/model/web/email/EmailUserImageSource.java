package com.shutafin.model.web.email;

import com.shutafin.annotations.annotations.LimitSize;
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
    @LimitSize
    private String imageSource;
}
