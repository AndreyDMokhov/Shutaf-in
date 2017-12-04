package com.shutafin.model.web.email;

import com.shutafin.model.web.email.response.ChangeEmailResponse;
import com.shutafin.model.web.email.response.RegistrationResponse;
import com.shutafin.model.web.email.response.ResetPasswordResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmailReason {
    REGISTRATION(1, "registration", RegistrationResponse.class),
    CHANGE_EMAIL(2, "changeEmail", ChangeEmailResponse.class),
    CHANGE_PASSWORD(3, "changePassword", Void.class),
    RESET_PASSWORD(4, "resetPassword", ResetPasswordResponse.class),
    MATCHING_CANDIDATES(5, "matchingCandidates", Void.class);

    private Integer id;
    private String propertyPrefix;
    private Class responseObject;


    public static EmailReason getById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        for (EmailReason emailReason : values()) {
            if (emailReason.getId().equals(id)) {
                return emailReason;
            }
        }

        throw new IllegalArgumentException("Email reason with ID [" + id + "] does not exist");
    }
}


