package com.CrimsonBackendDatabase.crimsondb.CompanyChatMessages;

import com.CrimsonBackendDatabase.crimsondb.CompanyMessageManager.CompanyMessageManager;
import com.CrimsonBackendDatabase.crimsondb.Utils.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyChatMessagesService {

    private final CompanyChatMessagesRepository companyChatMessagesRepository;

    @Autowired
    public CompanyChatMessagesService(CompanyChatMessagesRepository companyChatMessagesRepository) {
        this.companyChatMessagesRepository = companyChatMessagesRepository;
    }
    public void addMessage(ChatMessage message,Long receiverId, CompanyMessageManager companyMessageManager) {
        CompanyChatMessages companyChatMessage = new CompanyChatMessages(message.getMessage(),receiverId ,companyMessageManager);
        companyChatMessagesRepository.save(companyChatMessage);
    }
    @Transactional
    public void deleteMessage(Long id) {
        companyChatMessagesRepository.deleteById(id);
    }
    @Transactional
    public void deleteAllMessages(Long receiverId, CompanyMessageManager companyMessageManager) {
        companyChatMessagesRepository.deleteCompanyChatMessagesByCompanyAndReceiverId(companyMessageManager, receiverId);
    }

    public List<CompanyChatMessages> getMessages(Long receiverId, CompanyMessageManager companyMessageManager) {
        Optional<List<CompanyChatMessages>> messages = companyChatMessagesRepository.getCompanyChatMessagesByCompanyAndReceiverId(companyMessageManager,receiverId);
        return messages.orElseGet(ArrayList::new);
    }
}

