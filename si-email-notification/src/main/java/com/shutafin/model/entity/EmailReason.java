package com.shutafin.model.entity;

public enum EmailReason {
    REGISTRATION_CONFIRMATION(1, "registration"),
    CHANGE_EMAIL(2, "changeEmail"),
    CHANGE_PASSWORD(3, "changePassword"),
    RESET_PASSWORD(4, "resetPassword"),
    MATCHING_CANDIDATES(5, "matchingCandidates");

    private Integer id;
    private String propertyPrefix;

    EmailReason(Integer id, String propertyPrefix) {
        this.id = id;
        this.propertyPrefix = propertyPrefix;
    }

    public Integer getId() {
        return id;
    }

    public String getPropertyPrefix() {
        return propertyPrefix;
    }

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
