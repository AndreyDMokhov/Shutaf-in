package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by evgeny on 6/20/2017.
 */
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
