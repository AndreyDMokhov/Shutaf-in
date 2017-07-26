package com.shutafin.controller;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.DataResponse;
import com.shutafin.model.web.error.ErrorResponse;
import com.shutafin.model.web.error.errors.InputValidationError;

import java.lang.reflect.Type;

public class APIWebResponseDeserializer implements JsonDeserializer<APIWebResponse> {
    @Override
    public APIWebResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        DataResponse data =jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject().get("data"), DataResponse.class);

        ErrorResponse error = jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject().get("error"), InputValidationError.class);

        return new APIWebResponse(error, data);
    }
}
