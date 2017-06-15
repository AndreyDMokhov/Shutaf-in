package com.company.service.impl;

import com.company.model.User;
import com.company.repository.UserRepository;
import com.company.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void save(User user) {
        Map map = getValidationErrors(user, false);
        if (!map.isEmpty()) {
            throw new RuntimeException(map.toString());
        }

        userRepository.save(user);

    }

    @Override
    @Transactional
    public void update(User user) {
        Map map = getValidationErrors(user, true);
        if (!map.isEmpty()) {
            throw new RuntimeException(map.toString());
        }

        userRepository.update(user);
    }

    @Override
    @Transactional
    public User findByUserId(Integer userId) {
        Map<String, String> userIdValidationErrors = getUserIdValidationErrors(userId);
        if (!userIdValidationErrors.isEmpty()) {
            throw new RuntimeException(userIdValidationErrors.toString());
        }
        return userRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }

    private Map<String, String> getValidationErrors(User user, boolean isUserIdRequired) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isBlank(user.getFirstName())) {
            map.put("firstname", "Null");
        }
        if (user.getFirstName().length() > 50) {
            map.put("firstname", "Length > 50");
        }

        if (StringUtils.isBlank(user.getLastName())) {
            map.put("lastname", "Null");
        }

        if (user.getLastName().length() > 50) {
            map.put("lastname", "Length > 50");
        }

        if (isUserIdRequired) {

            Map<String, String> userIdValidationErrors = getUserIdValidationErrors(user.getUserId());
            if (!userIdValidationErrors.isEmpty()) {

                map.putAll(userIdValidationErrors);
            }
        }

        return map;
    }

    private Map<String, String> getUserIdValidationErrors(Integer userId) {
        Map<String, String> map = new HashMap<>();

        if (userId == null) {
            map.put("userId", "Null");
            return map;
        } else if (userId <= 0) {
            map.put("userId", "Zero or negative userId");
            return map;
        } else if (userId > Integer.MAX_VALUE) {
            map.put("userId", "Too big userId value");
            return map;
        }

        return map;
    }
}
