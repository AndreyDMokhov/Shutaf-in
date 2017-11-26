package com.shutafin.model.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class DealPanelWeb {

    private static final Long SHIFT_VALUE = 113L;
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long dealId;

    @Length(min = 3, max = 50)
    private String title;

    public DealPanelWeb(Long id, Long userId, Long dealId, String title) {
        setId(id);
        this.userId = userId;
        this.dealId = dealId;
        this.title = title;
    }

    public void setId(Long id) {
        this.id = id << SHIFT_VALUE;
    }

    public static Long getShiftValue() {
        return SHIFT_VALUE;
    }
}
