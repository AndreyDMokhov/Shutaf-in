package com.shutafin.model.web.initialization;

import com.shutafin.model.web.DataResponse;

/**
 * Created by evgeny on 6/23/2017.
 */
public class LanguageWeb implements DataResponse {
    private Integer id;
    private String description;
    private String nativeName;

    public LanguageWeb() {
    }

    public LanguageWeb(Integer id, String description, String nativeName) {
        this.id = id;
        this.description = description;
        this.nativeName = nativeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }
}
