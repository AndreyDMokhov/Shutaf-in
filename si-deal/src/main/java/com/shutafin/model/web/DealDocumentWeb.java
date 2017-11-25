package com.shutafin.model.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DealDocumentWeb {
    private Long documentId;
    private String title;
    private Integer documentType;
    private Long createdDate;
}
