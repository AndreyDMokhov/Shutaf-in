package com.shutafin.model.entities;

import com.shutafin.model.base.AbstractRemovableEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "DEAL_PANEL")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DealPanel extends AbstractRemovableEntity {

    @Column(name = "TITLE", length = 50, nullable = false)
    private String title;

    @JoinColumn(name = "DEAL_ID", nullable = false)
    @OneToOne
    private Deal deal;

}
