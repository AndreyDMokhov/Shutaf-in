package com.shutafin.model.entities;

import com.shutafin.model.base.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "USER_LOGIN_LOG")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserLoginLog extends AbstractEntity {
    @JoinColumn(name = "USER_ID", nullable = false)
    @OneToOne
    private User user;

    @Column(name = "IS_LOGIN_SUCCESS", nullable = false)
    private Boolean isLoginSuccess;

}
