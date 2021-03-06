package com.shutafin.model.entities;

import com.shutafin.model.base.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "DOCUMENT_STORAGE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DocumentStorage extends AbstractBaseEntity {

    @JoinColumn(name = "DEAL_DOCUMENT_ID")
    @OneToOne
    private DealDocument dealDocument;

    @Column(name = "BASE_64_DOCUMENT", nullable = false)
    @Lob
    private String documentEncoded;
}
