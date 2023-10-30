package com.CrimsonBackendDatabase.crimsondb.UserMessageManager;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import com.CrimsonBackendDatabase.crimsondb.Company.CompanyRepository;
import com.CrimsonBackendDatabase.crimsondb.CompanyChatMessages.CompanyChatMessagesService;
import com.CrimsonBackendDatabase.crimsondb.CompanyMessageManager.CompanyMessageManager;
import com.CrimsonBackendDatabase.crimsondb.CompanyMessageManager.CompanyMessageManagerRepository;
import com.CrimsonBackendDatabase.crimsondb.UserChatMessages.UserChatMessagesService;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import com.CrimsonBackendDatabase.crimsondb.Users.UsersRepository;
import com.CrimsonBackendDatabase.crimsondb.UserMessageManager.UserMessageManager;
import com.CrimsonBackendDatabase.crimsondb.UserMessageManager.UserMessageManagerRepository;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserToken;
import com.CrimsonBackendDatabase.crimsondb.UserMessageManager.UserMessagesException.InvalidReceiverException;
import com.CrimsonBackendDatabase.crimsondb.UserMessageManager.UserMessagesException.NoChatsException;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserToken;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenService;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import com.CrimsonBackendDatabase.crimsondb.Users.UsersRepository;
import com.CrimsonBackendDatabase.crimsondb.Utils.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserMessageManagerService {
    private final UserTokenService userTokenService;
    private final CompanyRepository companyRepository;
    private final UsersRepository usersRepository;
    private final UserMessageManagerRepository userMessageManagerRepository;
    private final CompanyMessageManagerRepository companyMessageManagerRepository;
    private final UserChatMessagesService userChatMessagesService;
    private final CompanyChatMessagesService companyChatMessagesService;

    @Autowired
    public UserMessageManagerService(UserTokenService userTokenService, CompanyRepository companyRepository, UsersRepository usersRepository, UserMessageManagerRepository userMessageManagerRepository, CompanyMessageManagerRepository companyMessageManagerRepository, UserChatMessagesService userChatMessagesService, CompanyChatMessagesService companyChatMessagesService) {
        this.userTokenService = userTokenService;
        this.companyRepository = companyRepository;
        this.usersRepository = usersRepository;
        this.userMessageManagerRepository = userMessageManagerRepository;
        this.companyMessageManagerRepository = companyMessageManagerRepository;
        this.userChatMessagesService = userChatMessagesService;
        this.companyChatMessagesService = companyChatMessagesService;
    }
    @Transactional
    public List<?> postUserMessage(String accessToken, ChatMessage message, Long receiverId, String receiverType) throws InvalidTokenException, InvalidReceiverException {
        Optional<UserToken> userToken = userTokenService.findUserToken(accessToken);
        if(userToken.isPresent()) {
            boolean isValid = userTokenService.validateToken(accessToken, String.valueOf(userToken.get().getUsers().getId()));
            if(isValid) {
                Optional<UserMessageManager> userPrimary = userMessageManagerRepository.findUserMessageManagerByUser(userToken.get().getUsers(), receiverId);
                if(userPrimary.isPresent()) {
                    userChatMessagesService.addMessage(message,receiverId,userPrimary.get());
                    return userChatMessagesService.getMessages(receiverId, userPrimary.get());
                } else {
                    if(Objects.equals(receiverType, "User")) {
                        Optional<Users> userReceiver = usersRepository.findById(receiverId);
                        if(userReceiver.isPresent()) {
                            Optional<UserMessageManager> userSecondary = userMessageManagerRepository.findUserMessageManagerByUser(userReceiver.get(), userToken.get().getUsers().getId());
                            if(userSecondary.isPresent()) {
                                userChatMessagesService.addMessage(message, userToken.get().getUsers().getId(), userSecondary.get());
                                return userChatMessagesService.getMessages(userToken.get().getUsers().getId(),userSecondary.get());
                            } else {
                                UserMessageManager userMessages = new UserMessageManager(receiverType,userToken.get().getUsers(), receiverId);
                                userMessageManagerRepository.save(userMessages);
                                userChatMessagesService.addMessage(message,receiverId,userMessages);
                                return userChatMessagesService.getMessages(receiverId,userMessages);
                            }
                        } else {
                            throw new InvalidReceiverException("Receiver id is invalid");
                        }
                    } else if (Objects.equals(receiverType, "Company")) {
                        Optional<Company> userReceiver = companyRepository.findById(receiverId);
                        if(userReceiver.isPresent()) {
                            Optional<CompanyMessageManager> userSecondary = companyMessageManagerRepository.findCompanyMessageManagerByCompany(userReceiver.get(), receiverId);
                            if(userSecondary.isPresent()) {
                                companyChatMessagesService.addMessage(message, userToken.get().getUsers().getId(), userSecondary.get());
                                return companyChatMessagesService.getMessages(userToken.get().getUsers().getId(),userSecondary.get());
                            } else {
                                UserMessageManager userMessages = new UserMessageManager(receiverType,userToken.get().getUsers(), receiverId);
                                userMessageManagerRepository.save(userMessages);
                                userChatMessagesService.addMessage(message,receiverId,userMessages);
                                return userChatMessagesService.getMessages(receiverId,userMessages);
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
    public HashMap<String, String> clearUsersMessages(String accessToken, Long receiverId, String receiverType) throws InvalidReceiverException, InvalidTokenException, NoChatsException {
        Optional<UserToken> userToken = userTokenService.findUserToken(accessToken);
        if(userToken.isPresent()) {
            boolean isValid = userTokenService.validateToken(accessToken, String.valueOf(userToken.get().getUsers().getId()));
            if(isValid) {
                Optional<UserMessageManager> userPrimary = userMessageManagerRepository.findUserMessageManagerByUser(userToken.get().getUsers(), receiverId);
                if(userPrimary.isPresent()) {
                    userChatMessagesService.deleteAllMessages(receiverId,userPrimary.get());
                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put("result", "success");
                    return data;
                } else {
                    if(Objects.equals(receiverType, "User")) {
                        Optional<Users> userReceiver = usersRepository.findById(receiverId);
                        if(userReceiver.isPresent()) {
                            Optional<UserMessageManager> userSecondary = userMessageManagerRepository.findUserMessageManagerByUser(userReceiver.get(), userToken.get().getUsers().getId());
                            if(userSecondary.isPresent()) {
                                userChatMessagesService.deleteAllMessages(userToken.get().getUsers().getId(), userSecondary.get());
                                HashMap<String, String> data = new HashMap<String, String>();
                                data.put("result", "success");
                                return data;
                            } else {
                                throw new NoChatsException("No Chats Found!");
                            }
                        } else {
                            throw new InvalidReceiverException("Receiver id is invalid");
                        }
                    } else if (Objects.equals(receiverType, "Company")) {
                        Optional<Company> userReceiver = companyRepository.findById(receiverId);
                        if(userReceiver.isPresent()) {
                            Optional<CompanyMessageManager> userSecondary = companyMessageManagerRepository.findCompanyMessageManagerByCompany(userReceiver.get(), receiverId);
                            if(userSecondary.isPresent()) {
                                companyChatMessagesService.deleteAllMessages(userToken.get().getUsers().getId(), userSecondary.get());
                                HashMap<String, String> data = new HashMap<String, String>();
                                data.put("result", "success");
                                return data;
                            } else {
                                throw  new NoChatsException("No chats Found!");
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

    };
    public HashMap<String, String> deleteUsersMessage(String accessToken, Long messageId) throws InvalidReceiverException, InvalidTokenException {
        Optional<UserToken> userToken = userTokenService.findUserToken(accessToken);
        if(userToken.isPresent()) {
            boolean isValid = userTokenService.validateToken(accessToken, String.valueOf(userToken.get().getUsers().getId()));
            if(isValid) {
                userChatMessagesService.deleteMessage(messageId);
                HashMap<String, String> res = new HashMap<String, String>();
                res.put("result", "success");
                return res;
            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw new InvalidTokenException();
        }

    };
}
