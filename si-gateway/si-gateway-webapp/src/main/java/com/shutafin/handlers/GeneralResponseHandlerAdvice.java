package com.shutafin.handlers;

import com.shutafin.annotations.InternalRestController;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.error.ErrorResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class GeneralResponseHandlerAdvice implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType
                .getMethod()
                .getDeclaringClass()
                .getAnnotation(InternalRestController.class) == null;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        APIWebResponse apiWebResponse = new APIWebResponse();

        if (isExceptionBody(body)) {
            apiWebResponse.setError((ErrorResponse) body);
        } else {
            apiWebResponse.setData(body);
        }
        return apiWebResponse;
    }

    private boolean isExceptionBody(Object body) {
        return body instanceof ErrorResponse;
    }
}
