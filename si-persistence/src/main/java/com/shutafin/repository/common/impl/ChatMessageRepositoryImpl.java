package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.ChatMessage;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.common.ChatMessageRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ChatMessageRepositoryImpl extends AbstractEntityDao<ChatMessage> implements ChatMessageRepository{
}
