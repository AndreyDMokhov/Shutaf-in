package com.shutafin.model.entities;

import com.shutafin.model.base.AbstractUserFile;
import com.shutafin.model.types.DocumentType;
import com.shutafin.model.types.DocumentTypeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "USER_DOCUMENT")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDocument extends AbstractUserFile {

    @Column(name = "DOCUMENT_TYPE_ID", nullable = false)
    @Convert(converter = DocumentTypeConverter.class)
    private DocumentType documentType;

    @JoinColumn(name = "DOCUMENT_STORAGE_ID")
    @OneToOne
    private DocumentStorage documentStorage;

}
