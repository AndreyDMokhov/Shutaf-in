package com.shutafin.model.entities.match;

import com.shutafin.model.AbstractBaseEntity;
import com.shutafin.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by evgeny on 9/5/2017.
 */
@Entity
@Table(name = "USER_EXAM_KEY")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserExamKey extends AbstractBaseEntity {

    @JoinColumn(name = "USER_ID", nullable = false)
    @OneToOne
    private User user;

    @Column(name = "EXAM_KEY", nullable = false)
    private String examKey;

    @Column(name = "EXAM_KEY_REGEXP", nullable = false)
    private String examKeyRegExp;

}
