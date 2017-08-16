package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.Chat;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.common.ChatRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ChatRepositoryImpl extends AbstractEntityDao<Chat> implements ChatRepository{
}
