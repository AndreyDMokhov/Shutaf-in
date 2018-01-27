package com.shutafin.model.entities;

import com.shutafin.model.base.AbstractDealFile;
import com.shutafin.model.web.deal.DocumentType;
import com.shutafin.model.types.DocumentTypeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "DEAL_DOCUMENT")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DealDocument extends AbstractDealFile {

    @Column(name = "DOCUMENT_TYPE_ID", nullable = false)
    @Convert(converter = DocumentTypeConverter.class)
    private DocumentType documentType;

    @JoinColumn(name = "DOCUMENT_STORAGE_ID")
    @OneToOne
    private DocumentStorage documentStorage;

    @Column(name = "TITLE", length = 50, nullable = false)
    private String title;

    @Column(name = "IS_DELETED", nullable = false)
    private Boolean isDeleted;

}
