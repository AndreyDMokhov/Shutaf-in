package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.ChatUser;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.common.ChatUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ChatUserRepositoryImpl extends AbstractEntityDao<ChatUser> implements ChatUserRepository{
}
