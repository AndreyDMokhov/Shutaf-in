package com.shutafin.model.entities;

import com.shutafin.model.base.AbstractRemovableEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "DEAL_FOLDER")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DealFolder extends AbstractRemovableEntity {

    @Column(name = "TITLE", length = 50, nullable = false)
    private String title;

    @JoinColumn(name = "DEAL_ID", nullable = false)
    @OneToOne
    private Deal deal;

}
