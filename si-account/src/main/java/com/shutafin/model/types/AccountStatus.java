package com.shutafin.model.types;


/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
public enum AccountStatus implements IdentifiableType<Integer> {
    NEW(1),
    CONFIRMED(2),
    COMPLETED_USER_INFO(3),
    COMPLETED_REQUIRED_MATCHING(4),
    DEAL(5),
    BLOCKED(-1);

    private Integer id;

    AccountStatus(Integer id) {
        this.id = id;
    }

    public Integer getCode() {
        return id;
    }

    public static AccountStatus getById(Integer id) {
        if (id == null) {
            return null;
        }

        for (AccountStatus accountStatus : values()) {
            if (accountStatus.getCode().equals(id)) {
                return accountStatus;
            }
        }

        throw new IllegalArgumentException(String.format("AccountStatus status with ID %d does not exist", id));
    }
}
