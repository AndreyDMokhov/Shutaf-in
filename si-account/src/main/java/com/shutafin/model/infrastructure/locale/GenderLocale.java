package com.shutafin.model.infrastructure.locale;

import com.shutafin.model.base.AbstractLocalizedConstEntity;
import com.shutafin.model.infrastructure.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "I_GENDER_LOCALE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GenderLocale extends AbstractLocalizedConstEntity {

    @JoinColumn(name = "GENDER_ID", nullable = false)
    @ManyToOne
    private Gender gender;

}
