package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.repository.UserCredentialsRepository;
import com.shutafin.service.PasswordService;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Created by Rogov on 04.07.2017.
 */
public class PasswordServiceImpl implements PasswordService {

    private static final int SALT_LEN = 16;
    private static final int ITERATIONS = 2;
    private static final int MEMORY = 65536;
    private static final int PARALLELISM = 2;
    private static final String SALT = "M<GLv!,ZAz/E-(4T";
    private final SecureRandom secureRandom = new SecureRandom();

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Override
    public void saveUserPasswordToDb(User user, String password) {
        Argon2 argon2 = Argon2Factory.create();
        String salt = generateSalt();
        String hash = argon2.hash(ITERATIONS, MEMORY, PARALLELISM, password+SALT+salt);

        System.out.println(salt+" "+hash);

        userCredentialsRepository.findUserByUserId(user);

    }

    private String generateSalt() {
        byte[] bytes = new byte[SALT_LEN];
        secureRandom.nextBytes(bytes);
        String salt = Base64.getEncoder().encodeToString(bytes);
        return salt;
    }


    @Override
    public void checkUserPassword(User user, String password) throws AuthenticationException {
        Argon2 argon2 = Argon2Factory.create();


    }


}
