package com.shutafin.service;

import com.shutafin.model.entities.User;

public interface ChatManagementService {

    String generateNewChat(User userOwner, User userPartner);
}
