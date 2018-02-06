package com.shutafin.system;

import com.google.gson.*;
import com.shutafin.model.error.ErrorResponse;
import com.shutafin.model.error.ErrorType;
import com.shutafin.model.error.errors.InputValidationError;
import com.shutafin.model.web.APIWebResponse;

import java.lang.reflect.Type;

public class APIWebResponseDeserializer implements JsonDeserializer<APIWebResponse> {

    private Class className;


    public APIWebResponseDeserializer(Class className) {
        this.className = className;
    }

    @Override
    public APIWebResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Object data = null;
        if (jsonElement.getAsJsonObject().get("data").isJsonObject() && className != null) {
            data = jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject().get("data"), className);
        }

        ErrorResponse errorResponse = null;
        if (jsonElement.getAsJsonObject().get("error").isJsonObject()) {
            JsonObject error = jsonElement.getAsJsonObject().get("error").getAsJsonObject();
            String errorTypeCode = error.getAsJsonObject().get("errorTypeCode").getAsString();
            ErrorType errorType = ErrorType.getById(errorTypeCode);

            if (errorType == ErrorType.INPUT) {
                errorResponse = jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject().get("error"), InputValidationError.class);
            } else {
                errorResponse = jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject().get("error"), ErrorResponse.class);
            }
        }

        return new APIWebResponse(errorResponse, data);
    }

}
