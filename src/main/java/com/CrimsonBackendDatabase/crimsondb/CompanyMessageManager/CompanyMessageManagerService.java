package com.CrimsonBackendDatabase.crimsondb.CompanyMessageManager;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import com.CrimsonBackendDatabase.crimsondb.Company.CompanyRepository;
import com.CrimsonBackendDatabase.crimsondb.CompanyChatMessages.CompanyChatMessages;
import com.CrimsonBackendDatabase.crimsondb.CompanyChatMessages.CompanyChatMessagesService;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyToken;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenService;
import com.CrimsonBackendDatabase.crimsondb.UserChatMessages.UserChatMessagesService;
import com.CrimsonBackendDatabase.crimsondb.UserMessageManager.UserMessageManager;
import com.CrimsonBackendDatabase.crimsondb.UserMessageManager.UserMessagesException.InvalidReceiverException;
import com.CrimsonBackendDatabase.crimsondb.UserMessageManager.UserMessageManagerRepository;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import com.CrimsonBackendDatabase.crimsondb.Users.UsersRepository;
import com.CrimsonBackendDatabase.crimsondb.Utils.ChatMessage;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CompanyMessageManagerService {
    private final CompanyTokenService companyTokenService;
    private final UsersRepository usersRepository;
    private final CompanyRepository companyRepository;
    private final UserMessageManagerRepository userMessageManagerRepository;
    private final CompanyMessageManagerRepository companyMessageManagerRepository;
    private final CompanyChatMessagesService companyChatMessagesService;
    private final UserChatMessagesService userChatMessagesService;

    @Autowired
    public CompanyMessageManagerService(CompanyTokenService companyTokenService, UsersRepository usersRepository, CompanyRepository companyRepository, UserMessageManagerRepository userMessageManagerRepository, CompanyMessageManagerRepository companyMessageManagerRepository, CompanyChatMessagesService companyChatMessagesService, UserChatMessagesService userChatMessagesService) {
        this.companyTokenService = companyTokenService;
        this.usersRepository = usersRepository;
        this.companyRepository = companyRepository;
        this.userMessageManagerRepository = userMessageManagerRepository;
        this.companyMessageManagerRepository = companyMessageManagerRepository;
        this.companyChatMessagesService = companyChatMessagesService;
        this.userChatMessagesService = userChatMessagesService;
    }
    @Transactional
    public List<?> postCompanyMessage(String accessToken, ChatMessage message, Long receiverId, String receiverType) throws InvalidTokenException, InvalidReceiverException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if(companyToken.isPresent()) {
            boolean isValid = companyTokenService.validateToken(accessToken, String.valueOf(companyToken.get().getCompany().getId()));
            if(isValid) {
                Optional<CompanyMessageManager> userPrimary = companyMessageManagerRepository.findCompanyMessageManagerByCompany(companyToken.get().getCompany(), receiverId);
                if(userPrimary.isPresent()) {
                    companyChatMessagesService.addMessage(message,receiverId,userPrimary.get());
                    return companyChatMessagesService.getMessages(receiverId, userPrimary.get());
                } else {
                    if(Objects.equals(receiverType, "User")) {
                        Optional<Users> userReceiver = usersRepository.findById(receiverId);
                        if(userReceiver.isPresent()) {
                            Optional<UserMessageManager> userSecondary = userMessageManagerRepository.findUserMessageManagerByUser(userReceiver.get(), companyToken.get().getCompany().getId());
                            if(userSecondary.isPresent()) {
                                userChatMessagesService.addMessage(message, companyToken.get().getCompany().getId(), userSecondary.get());
                                return userChatMessagesService.getMessages(companyToken.get().getCompany().getId(),userSecondary.get());
                            } else {
                                CompanyMessageManager companyMessages = new CompanyMessageManager(receiverId,receiverType, companyToken.orElseThrow().getCompany());
                                companyMessageManagerRepository.save(companyMessages);
                                companyChatMessagesService.addMessage(message,receiverId,companyMessages);
                                return companyChatMessagesService.getMessages(receiverId,companyMessages);
                            }
                        } else {
                            throw new InvalidReceiverException("Receiver id is invalid");
                        }
                    } else if (Objects.equals(receiverType, "Company")) {
                        Optional<Company> companyReceiver = companyRepository.findById(receiverId);
                        if(companyReceiver.isPresent()) {
                            Optional<CompanyMessageManager> userSecondary = companyMessageManagerRepository.findCompanyMessageManagerByCompany(companyReceiver.get(), receiverId);
                            if(userSecondary.isPresent()) {
                                companyChatMessagesService.addMessage(message, companyToken.get().getCompany().getId(), userSecondary.get());
                                return companyChatMessagesService.getMessages(companyToken.get().getCompany().getId(),userSecondary.get());
                            } else {
                                CompanyMessageManager companyMessages = new CompanyMessageManager(receiverId,receiverType, companyToken.get().getCompany());
                                companyMessageManagerRepository.save(companyMessages);
                                companyChatMessagesService.addMessage(message,receiverId,companyMessages);
                                return companyChatMessagesService.getMessages(receiverId,companyMessages);
                            }
                        }else {
                            throw new InvalidReceiverException("Receiver id is invalid");
                        }
                    } else {
                        throw new InvalidReceiverException("Receiver type is invalid");
                    }
                }
            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw new InvalidTokenException();
        }
    }
    public HashMap<String, String> clearCompanyMessages(String accessToken, Long receiverId, String receiverType) throws InvalidReceiverException, InvalidTokenException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if(companyToken.isPresent()) {
            boolean isValid = companyTokenService.validateToken(accessToken, String.valueOf(companyToken.get().getCompany().getId()));
            if(isValid) {
                Optional<CompanyMessageManager> userPrimary = companyMessageManagerRepository.findCompanyMessageManagerByCompany(companyToken.get().getCompany(), receiverId);
                if(userPrimary.isPresent()) {

                } else {
                    if(Objects.equals(receiverType, "User")) {
                        Optional<Users> userReceiver = usersRepository.findById(receiverId);
                        if(userReceiver.isPresent()) {
                            Optional<UserMessageManager> userSecondary = userMessageManagerRepository.findUserMessageManagerByUser(userReceiver.get(), companyToken.get().getCompany().getId());
                            if(userSecondary.isPresent()) {

                            } else {

                            }
                        } else {
                            throw new InvalidReceiverException("Receiver id is invalid");
                        }
                    } else if (Objects.equals(receiverType, "Company")) {
                        Optional<Company> companyReceiver = companyRepository.findById(receiverId);
                        if(companyReceiver.isPresent()) {
                            Optional<CompanyMessageManager> userSecondary = companyMessageManagerRepository.findCompanyMessageManagerByCompany(companyReceiver.get(), receiverId);
                            if(userSecondary.isPresent()) {

                            } else {

                            }
                        }else {
                            throw new InvalidReceiverException("Receiver id is invalid");
                        }
                    }
                }
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("result", "success");
                return data;
            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw new InvalidTokenException();
        }

    };
    public HashMap<String, String> deleteCompanyMessage(String accessToken, Long receiverId, String receiverType) throws InvalidReceiverException, InvalidTokenException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if(companyToken.isPresent()) {
            boolean isValid = companyTokenService.validateToken(accessToken, String.valueOf(companyToken.get().getCompany().getId()));
            if(isValid) {
                Optional<CompanyMessageManager> userPrimary = companyMessageManagerRepository.findCompanyMessageManagerByCompany(companyToken.get().getCompany(), receiverId);
                if(userPrimary.isPresent()) {

                } else {
                    if(Objects.equals(receiverType, "User")) {
                        Optional<Users> userReceiver = usersRepository.findById(receiverId);
                        if(userReceiver.isPresent()) {
                            Optional<UserMessageManager> userSecondary = userMessageManagerRepository.findUserMessageManagerByUser(userReceiver.get(), companyToken.get().getCompany().getId());
                            if(userSecondary.isPresent()) {

                            } else {

                            }
                        } else {
                            throw new InvalidReceiverException("Receiver id is invalid");
                        }
                    } else if (Objects.equals(receiverType, "Company")) {
                        Optional<Company> companyReceiver = companyRepository.findById(receiverId);
                        if(companyReceiver.isPresent()) {
                            Optional<CompanyMessageManager> userSecondary = companyMessageManagerRepository.findCompanyMessageManagerByCompany(companyReceiver.get(), receiverId);
                            if(userSecondary.isPresent()) {

                            } else {

                            }
                        }else {
                            throw new InvalidReceiverException("Receiver id is invalid");
                        }
                    }
                }
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("result", "success");
                return data;
            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw new InvalidTokenException();
        }

    };
}
