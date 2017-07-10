package com.shutafin.model.entities.types;


/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
public enum AccountStatus {
    NEW(1),
    CONFIRMED(2),
    BLOCKED(3);

    private Integer id;

    AccountStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static AccountStatus getById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Account status ID cannot be null");
        }

        for (AccountStatus accountStatus : values()) {
            if (accountStatus.getId().equals(id)) {
                return accountStatus;
            }
        }

        throw new IllegalArgumentException("Account status with ID [" + id + "] does not exist");
    }
}
