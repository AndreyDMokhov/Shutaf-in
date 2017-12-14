package com.shutafin.model.web.deal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DealWeb {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long dealId;
    private String title;

    @NotEmpty
    private List<Long> users;
}
