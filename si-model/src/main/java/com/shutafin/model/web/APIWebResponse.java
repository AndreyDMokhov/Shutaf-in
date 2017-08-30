package com.shutafin.model.web;


import com.shutafin.model.web.error.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class APIWebResponse {

    private ErrorResponse error;
    private Object data;

}
