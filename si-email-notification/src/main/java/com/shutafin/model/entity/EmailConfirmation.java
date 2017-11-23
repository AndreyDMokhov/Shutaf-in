package com.shutafin.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "CONFIRMATION")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmailConfirmation extends Confirmation {

    @Column(name = "NEW_EMAIL")
    private String newEmail;

    @JoinColumn(name = "CONNECTED_CONFIRMATION")
    @OneToOne
    private EmailConfirmation connectedEmailConfirmation;

}
