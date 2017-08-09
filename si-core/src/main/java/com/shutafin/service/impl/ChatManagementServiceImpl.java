package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.service.ChatManagementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChatManagementServiceImpl implements ChatManagementService {

    @Override
    public String generateNewChat(User userOwner, User userPartner) {
        return null;
    }
}
