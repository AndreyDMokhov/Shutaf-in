package com.shutafin.model.entities.match;

import com.shutafin.model.AbstractBaseEntity;
import com.shutafin.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by evgeny on 9/5/2017.
 */
@Deprecated
@Entity
@Table(name = "USER_EXAM_KEY")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
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
