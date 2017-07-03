package com.shutafin.model.entities.types;

/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
public enum EmailReason {
    REGISTRATION_CONFIRMATION(1),
    CHANGE_EMAIL(2),
    CHANGE_PASSWORD(3),
    RESET_PASSWORD(4);

    private Integer id;

    EmailReason(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
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
