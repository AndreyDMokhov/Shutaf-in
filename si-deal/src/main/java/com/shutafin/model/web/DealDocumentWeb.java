package com.shutafin.model.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DealDocumentWeb {

    private static final Long SHIFT_VALUE = 131L;

    private Long documentId;
    private String title;
    private Integer documentType;
    private Long createdDate;

    public DealDocumentWeb(Long documentId, String title, Integer documentType, Long createdDate) {
        setDocumentId(documentId);
        this.title = title;
        this.documentType = documentType;
        this.createdDate = createdDate;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId << SHIFT_VALUE;
    }

    public static Long getShiftValue() {
        return SHIFT_VALUE;
    }
}
