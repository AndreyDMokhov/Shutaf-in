package com.shutafin.model.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DealWeb {
    private Long dealId;

    @NotNull
    private Long originUserId;

    private Integer dealStatusId;

    @Length(min = 3, max = 50)
    private String title;

    private Map<Long, String> dealFolders;

    @NotEmpty
    private List<Long> userIds;
}
