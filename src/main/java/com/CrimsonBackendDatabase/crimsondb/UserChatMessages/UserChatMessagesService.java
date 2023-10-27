package com.CrimsonBackendDatabase.crimsondb.UserChatMessages;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import com.CrimsonBackendDatabase.crimsondb.UserMessageManager.UserMessageManager;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import com.CrimsonBackendDatabase.crimsondb.Utils.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserChatMessagesService {

    private final UserChatMessagesRepository userChatMessagesRepository;

    @Autowired
    public UserChatMessagesService(UserChatMessagesRepository userChatMessagesRepository) {
        this.userChatMessagesRepository = userChatMessagesRepository;
    }
    public void addMessage(ChatMessage message,Long receiverId, UserMessageManager userMessageManager) {
        UserChatMessages companyChatMessage = new UserChatMessages(message.getMessage(),receiverId ,userMessageManager);
        userChatMessagesRepository.save(companyChatMessage);
    }
    @Transactional
    public void deleteMessage(Long id) {
        userChatMessagesRepository.deleteById(id);
    }
    @Transactional
    public void deleteAllMessages(Long receiverId, UserMessageManager userMessageManager) {
        userChatMessagesRepository.getUserChatMessagesByUserAndReceiverId(userMessageManager, receiverId);
    }

    public List<UserChatMessages> getMessages(Long receiverId, UserMessageManager userMessageManager) {
        Optional<List<UserChatMessages>> messages = userChatMessagesRepository.getUserChatMessagesByUserAndReceiverId(userMessageManager,receiverId);
        return messages.orElseGet(ArrayList::new);
    }
}


