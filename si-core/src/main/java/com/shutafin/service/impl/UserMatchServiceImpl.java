package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.repository.common.UserMandatoryMatchRepository;
import com.shutafin.service.UserMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evgeny on 8/12/2017.
 */
@Service
@Transactional
public class UserMatchServiceImpl implements UserMatchService {

    @Autowired
    private UserMandatoryMatchRepository userMandatoryMatchRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> findPartners(User user) {
        if (user ==null)
            return new ArrayList<User>();
        return userMandatoryMatchRepository.findPartners(user);
    }
}
