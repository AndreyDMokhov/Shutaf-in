package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Deprecated
@Entity
@Table(name = "REGISTRATION_CONFIRMATION")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegistrationConfirmation extends AbstractEntity {

    @JoinColumn(name = "USER_ID", nullable = false, unique = true)
    @OneToOne
    private User user;

    @Column(name = "URL_LINK", nullable = false, length = 50, unique = true)
    private String urlLink;

    @Column(name = "IS_CONFIRMED", nullable = false)
    private Boolean isConfirmed;

}
