package com.shutafin.model.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class DealUserDocumentWeb {

    private static final Long SHIFT_VALUE = 131L;

    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long dealFolderId;

    @NotBlank
    private String fileData;

    private Long createdDate;

    @NotNull
    private Integer documentTypeId;

    @Length(min = 1, max = 50)
    private String documentTitle;

    public DealUserDocumentWeb(Long id, Long userId, Long dealFolderId, String fileData, Long createdDate,
                               Integer documentTypeId, String documentTitle) {
        setId(id);
        this.userId = userId;
        this.dealFolderId = dealFolderId;
        this.fileData = fileData;
        this.createdDate = createdDate;
        this.documentTypeId = documentTypeId;
        this.documentTitle = documentTitle;
    }

    public void setId(Long id) {
        this.id = id << SHIFT_VALUE;
    }

    public Long getId() {
        return id >> SHIFT_VALUE;
    }
}
