package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.service.PasswordService;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Created by Rogov on 04.07.2017.
 */
public class PasswordServiceImpl implements PasswordService {

    private static final int SALT_LEN = 16;
    private static final int ITERATIONS = 2;
    private static final int MEMORY = 65536 ;
    private static final int PARARELLISM = 2 ;
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public void saveUserPasswordToDb(User user, String password) {
        Argon2 argon2 = Argon2Factory.create();
        byte [] bytes = generateSalt();
        Arrays.stream(bytes).stream.
//        String value = new String(bytes, "UTF-8");

        String hash = argon2.hash(ITERATIONS, MEMORY, PARARELLISM, password, charset);

    }

    @Override
    public void checkUserPassword(User user, String password) throws AuthenticationException {

    }

    private byte[] generateSalt() {
        byte[] salt = new byte[SALT_LEN];
        secureRandom.nextBytes(salt);
        return salt;
    }
}
