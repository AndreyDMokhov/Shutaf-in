package com.shutafin.model.web.user;

import com.shutafin.model.web.DataResponse;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

/**
 * Created by evgeny on 7/10/2017.
 */
public class RegistrationConfirmationWeb implements DataResponse {

    @Min(value = 1)
    private Long userId;

    @NotBlank
    private String urlLink;

    public RegistrationConfirmationWeb() {
    }

    public RegistrationConfirmationWeb(Long userId, String urlLink) {
        this.userId = userId;
        this.urlLink = urlLink;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }
}
