package com.shutafin.model.web.deal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InternalDealWeb {

    private Long dealId;
    private Long originUserId;

    @Length(max = 255)
    private String title;

    @NotEmpty
    private List<Long> users;
}
