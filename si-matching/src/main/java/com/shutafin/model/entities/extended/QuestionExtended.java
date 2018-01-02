package com.shutafin.model.entities.extended;


import com.shutafin.model.base.AbstractKeyConstEntity;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "I_QUESTION_EXTENDED")
@NoArgsConstructor
public class QuestionExtended extends AbstractKeyConstEntity {

}
