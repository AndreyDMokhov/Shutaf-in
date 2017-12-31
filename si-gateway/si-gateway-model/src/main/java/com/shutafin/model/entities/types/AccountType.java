package com.shutafin.model.entities.types;

/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
@Deprecated
public enum AccountType {
    REGULAR(1),
    ADMIN(2);

    private Integer id;

    AccountType(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static AccountType getById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Account type ID cannot be null");
        }

        for (AccountType accountType : values()) {
            if (accountType.getId().equals(id)) {
                return accountType;
            }
        }

        throw new IllegalArgumentException("Account type with ID [" + id + "] does not exist");
    }
}
