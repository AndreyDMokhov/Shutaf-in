package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;
import com.shutafin.model.entities.infrastructure.Answer;
import com.shutafin.model.entities.infrastructure.Question;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by evgeny on 8/10/2017.
 */
@Entity
@Table(name = "USER_MANDATORY_MATCH_RESULT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserMandatoryMatchResult extends AbstractEntity {

    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne
    private User users;

    @JoinColumn(name = "QUESTION_ID", nullable = false)
    @ManyToOne
    private Question questions;

    @JoinColumn(name = "ANSWER_ID", nullable = false)
    @ManyToOne
    private Answer answers;

}
