package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInit;
import com.shutafin.repository.initialization.custom.UserInitializationRepository;
import com.shutafin.service.UserInitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserInitializationServiceImpl implements UserInitializationService {


    @Autowired
    private UserInitializationRepository userInitializationRepository;

    @Override
    @Transactional
    public UserInit getUserInitData(User user) {

        UserInit userInit = userInitializationRepository.getUserInitializationData(user);
        return userInit;
    }
}
