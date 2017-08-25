package com.shutafin.model.entities.matching;

import com.shutafin.model.AbstractEntity;
import com.shutafin.model.entities.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "USER_MATCHING_SCORE")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
public class UserMatchingScore extends AbstractEntity {

    @JoinColumn(name = "USER_ORIGIN_ID", nullable = false)
    @ManyToOne
    private User userOrigin;

    @JoinColumn(name = "USER_TO_MATCH_ID", nullable = false)
    @ManyToOne
    private User userToMatch;

    @Column(name = "SCORE", nullable = false)
    private Integer score;

    public UserMatchingScore() {
    }

    public UserMatchingScore(User userOrigin, User userToMatch, Integer score) {
        this.userOrigin = userOrigin;
        this.userToMatch = userToMatch;
        this.score = score;
    }

    public User getUserOrigin() {
        return userOrigin;
    }

    public void setUserOrigin(User userOrigin) {
        this.userOrigin = userOrigin;
    }

    public User getUserToMatch() {
        return userToMatch;
    }

    public void setUserToMatch(User userToMatch) {
        this.userToMatch = userToMatch;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
