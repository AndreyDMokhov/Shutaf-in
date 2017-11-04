package com.shutafin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SI-ADMIN <-> SI-GATEWAY
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long createdDate;
    private Long updatedDate;
}
