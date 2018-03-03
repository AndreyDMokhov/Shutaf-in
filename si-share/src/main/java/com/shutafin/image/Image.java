package com.shutafin.image;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.Base64;

public class Image {

    @SneakyThrows
    public static String getImage(Class zclass, String imageName) {
        InputStream resourceAsStream = zclass.getClassLoader().getResourceAsStream(imageName);
        if (resourceAsStream != null) {
            byte[] imageDecoded = new byte[resourceAsStream.available()];
            resourceAsStream.read(imageDecoded);
            resourceAsStream.close();
            return Base64.getEncoder().encodeToString(imageDecoded);
        }
        return null;
    }

}
