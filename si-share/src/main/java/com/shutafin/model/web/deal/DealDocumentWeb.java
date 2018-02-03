package com.shutafin.model.web.deal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DealDocumentWeb {

    private Long documentId;
    private String title;
    private DocumentType documentType;
    private Long createdDate;

}
