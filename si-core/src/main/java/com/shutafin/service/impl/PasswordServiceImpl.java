package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.SystemException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserCredentials;
import com.shutafin.repository.account.UserCredentialsRepository;
import com.shutafin.service.PasswordService;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@Transactional
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
    @Transactional
    public void createAndSaveUserPassword(User user, String password) {
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUser(user);
        userCredentialsRepository.save(populateWithSaltAndHash(userCredentials, password));
    }

    @Override
    @Transactional
    public void updateUserPasswordInDb(User user, String password) {
        UserCredentials userCredentials = userCredentialsRepository.findUserByUserId(user);
        if (userCredentials == null) {
            throw new SystemException("UserCredentials for user with ID {" + user.getId() + "} does not exist");
        }
        userCredentialsRepository.update(populateWithSaltAndHash(userCredentials, password));
    }

    @Override
    @Transactional
    public boolean isPasswordCorrect(User user, String password) {
        Argon2 argon2 = Argon2Factory.create();
        UserCredentials userCredentials = userCredentialsRepository.findUserByUserId(user);
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
        String hash = argon2.hash(ITERATIONS, MEMORY, PARALLELISM, password + SALT + salt);
        return hash;
    }

    private String generateSalt() {
        byte[] bytes = new byte[SALT_LEN];
        secureRandom.nextBytes(bytes);
        String salt = Base64.getEncoder().encodeToString(bytes);
        return salt;
    }
}