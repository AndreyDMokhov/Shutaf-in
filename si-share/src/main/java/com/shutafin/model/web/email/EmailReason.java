package com.shutafin.model.web.email;

import com.shutafin.model.web.email.response.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmailReason {
    REGISTRATION(1, "registration", EmailRegistrationResponse.class),
    EMAIL_CHANGE(2, "emailChange", EmailChangeResponse.class),
    CHANGE_PASSWORD(3, "changePassword", Void.class),
    RESET_PASSWORD(4, "resetPassword", EmailResetPasswordResponse.class),
    MATCHING_CANDIDATES(5, "matchingCandidates", Void.class),
    DEAL_CREATION(6, "dealCreation", EmailDealCreationResponse.class),
    DEAL_USER_ADDING(7, "dealUserAdding", EmailDealUserAddingResponse.class),
    DEAL_USER_REMOVING(8, "dealUserRemoving", EmailDealUserRemovingResponse.class);

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


