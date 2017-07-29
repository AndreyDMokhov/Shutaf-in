package com.shutafin.controller;

import com.google.gson.*;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.DataResponse;
import com.shutafin.model.web.error.ErrorResponse;
import com.shutafin.model.web.error.ErrorType;
import com.shutafin.model.web.error.errors.InputValidationError;

import java.lang.reflect.Type;

public class APIWebResponseDeserializer implements JsonDeserializer<APIWebResponse> {

    @Override
    public APIWebResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        DataResponse data = jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject().get("data"), DataResponse.class);

        JsonObject error = jsonElement.getAsJsonObject().get("error").getAsJsonObject();
        String errorTypeCode = error.getAsJsonObject().get("errorTypeCode").getAsString();

        ErrorResponse errorResponse;
        ErrorType errorType = ErrorType.getById(errorTypeCode);

        if (errorType == ErrorType.INPUT) {
            errorResponse = jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject().get("error"), InputValidationError.class);
        } else {
            errorResponse = jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject().get("error"), ErrorResponse.class);
        }


        return new APIWebResponse(errorResponse, data);
    }
}