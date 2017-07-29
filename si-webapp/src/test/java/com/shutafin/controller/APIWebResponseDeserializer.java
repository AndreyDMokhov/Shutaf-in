package com.shutafin.controller;

import com.google.gson.*;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.DataResponse;
import com.shutafin.model.web.account.UserLanguageWeb;
import com.shutafin.model.web.error.ErrorResponse;
import com.shutafin.model.web.error.ErrorType;
import com.shutafin.model.web.error.errors.InputValidationError;
import com.shutafin.model.web.initialization.LanguageWeb;
import com.shutafin.model.web.user.*;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class APIWebResponseDeserializer implements JsonDeserializer<APIWebResponse> {

    @Override
    public APIWebResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        DataResponse data = jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject().get("data"),
                getClassName(jsonElement));
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

    private <T extends DataResponse> Class<T> getClassName(JsonElement jsonElement) {
        List<Class<T>> classNames = getClassesNames();

        HashSet<String> jsonKeys = new HashSet<>();
        for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().get("data").getAsJsonObject().entrySet()) {
            jsonKeys.add(entry.getKey());
        }

        for (Class<T> className : classNames) {
            if (classContainsAllFields(className, jsonKeys)) {
                return className;
            }
        }
        return null;
    }

    private <T extends DataResponse> List<Class<T>> getClassesNames() {
        List<Class<T>> classNames = new ArrayList<>();
        classNames.add((Class<T>) UserLanguageWeb.class);
        classNames.add((Class<T>) LanguageWeb.class);
        classNames.add((Class<T>) RegistrationRequestWeb.class);
        classNames.add((Class<T>) UserAccountSettingsWeb.class);
        classNames.add((Class<T>) UserImageWeb.class);
        classNames.add((Class<T>) UserInfoWeb.class);
        classNames.add((Class<T>) UserInit.class);
        return classNames;
    }

    private <T extends DataResponse> boolean classContainsAllFields(Class<T> className, HashSet<String> jsonKeys) {
        HashSet<String> fieldNames = new HashSet<>();
        for (Field field : className.getDeclaredFields()) {
            fieldNames.add(field.getName());
        }
        for (String jsonKey : jsonKeys) {
            if (!fieldNames.contains(jsonKey)) {
                return false;
            }
        }
        return true;
    }

}
