package com.shutafin.core.service.impl;


import com.shutafin.core.service.PasswordService;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserCredentials;
import com.shutafin.model.exception.exceptions.SystemException;
import com.shutafin.repository.account.UserCredentialsRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@Transactional
@Slf4j
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
    public void createAndSaveUserPassword(User user, String password) {
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUser(user);
        userCredentialsRepository.save(populateWithSaltAndHash(userCredentials, password));
    }

    @Override
    public void updateUserPasswordInDb(User user, String password) {
        UserCredentials userCredentials = userCredentialsRepository.findByUser(user);
        if (userCredentials == null) {
            log.error("System exception:");
            log.error("UserCredentials for user with ID {} does not exist", user.getId());
            throw new SystemException("UserCredentials for user with ID {" + user.getId() + "} does not exist");
        }
        userCredentials = populateWithSaltAndHash(userCredentials, password);
    }

    @Override
    public boolean isPasswordCorrect(User user, String password) {
        Argon2 argon2 = Argon2Factory.create();
        UserCredentials userCredentials = userCredentialsRepository.findByUser(user);
        String hash = userCredentials.getPasswordHash();
        String salt = userCredentials.getPasswordSalt();
        return argon2.verify(hash, password + SALT + salt);
    }

    private UserCredentials populateWithSaltAndHash(UserCredentials userCredentials, String password) {
        String salt = generateSalt();
        String hash = generateHash(salt, password);

        userCredentials.setPasswordHash(hash);
        userCredentials.setPasswordSalt(salt);
        return userCredentials;
    }

    private String generateHash(String salt, String password) {
        Argon2 argon2 = Argon2Factory.create();
        return argon2.hash(ITERATIONS, MEMORY, PARALLELISM, password + SALT + salt);
    }

    private String generateSalt() {
        byte[] bytes = new byte[SALT_LEN];
        secureRandom.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }
}