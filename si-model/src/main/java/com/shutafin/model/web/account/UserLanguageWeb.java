package com.shutafin.model.web.account;

import com.shutafin.model.web.DataResponse;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by evgeny on 6/26/2017.
 */
public class UserLanguageWeb implements DataResponse {

    @NotNull
    @Min(1)
    private Integer id;

    public UserLanguageWeb() {
    }

    public UserLanguageWeb(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
