package com.shutafin.model.types;

public enum DocumentType implements IdentifiableType<Integer> {
    DOC(0, ".doc", "0M8R4KGxGuE"),
    DOCX(1, ".docx", "UEsDBBQA"),
    PDF(2, ".pdf", "JVBER"),
    TIF(3, ".tif", "SUkqA", "TU0AKg");

    private Integer id;

    /*File signature is unique data to identify file content according to it's HEX value
    In this case HEX value is converted to Base64*/
    private String[] fileSignatures;
    private String fileExtension;

    DocumentType(Integer id, String fileExtension, String... fileSignatures) {
        this.id = id;
        this.fileExtension = fileExtension;
        this.fileSignatures = fileSignatures;
    }

    @Override
    public Integer getCode() {
        return id;
    }

    public static DocumentType getById(Integer id) {
        if (id == null) {
            return null;
        }

        for (DocumentType documentType : values()) {
            if (documentType.getCode().equals(id)) {
                return documentType;
            }
        }

        throw new IllegalArgumentException(String.format("Document type with ID %d does not exist", id));
    }

    public String[] getFileSignature() {
        return fileSignatures;
    }

    public String getFileExtension() {
        return fileExtension;
    }
}
