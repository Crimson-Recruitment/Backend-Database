package com.CrimsonBackendDatabase.crimsondb.CompanyMessages;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import com.CrimsonBackendDatabase.crimsondb.Company.CompanyRepository;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyToken;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenService;
import com.CrimsonBackendDatabase.crimsondb.UserMessages.UserMessages;
import com.CrimsonBackendDatabase.crimsondb.UserMessages.UserMessagesException.InvalidReceiverException;
import com.CrimsonBackendDatabase.crimsondb.UserMessages.UserMessagesRepository;
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
public class CompanyMessagesService {
    private final CompanyTokenService companyTokenService;
    private final UsersRepository usersRepository;
    private final CompanyRepository companyRepository;
    private final UserMessagesRepository userMessagesRepository;
    private final CompanyMessagesRepository companyMessagesRepository;

    @Autowired
    public CompanyMessagesService(CompanyTokenService companyTokenService, UsersRepository usersRepository, CompanyRepository companyRepository,UserMessagesRepository userMessagesRepository, CompanyMessagesRepository companyMessagesRepository) {
        this.companyTokenService = companyTokenService;
        this.usersRepository = usersRepository;
        this.companyRepository = companyRepository;
        this.userMessagesRepository = userMessagesRepository;
        this.companyMessagesRepository = companyMessagesRepository;
    }
    @Transactional
    public List<String> postCompanyMessage(String accessToken, ChatMessage message, Long receiverId, String receiverType) throws InvalidTokenException, InvalidReceiverException {
        Optional<CompanyToken> companyToken = companyTokenService.findCompanyToken(accessToken);
        if(companyToken.isPresent()) {
            boolean isValid = companyTokenService.validateToken(accessToken, String.valueOf(companyToken.get().getCompany().getId()));
            if(isValid) {
                Optional<CompanyMessages> userPrimary = companyMessagesRepository.findCompanyMessagesByCompany(companyToken.get().getCompany(), receiverId);
                if(userPrimary.isPresent()) {
                    List<String> list = userPrimary.get().getMessageArray();
                    list.add(new Gson().toJson(message));
                    return list;
                } else {
                    if(Objects.equals(receiverType, "User")) {
                        Optional<Users> userReceiver = usersRepository.findById(receiverId);
                        if(userReceiver.isPresent()) {
                            Optional<UserMessages> userSecondary = userMessagesRepository.findUserMessagesByUser(userReceiver.get(), companyToken.get().getCompany().getId());
                            if(userSecondary.isPresent()) {
                                List<String> list = userSecondary.get().getMessageArray();
                                list.add(new Gson().toJson(message));
                                return list;
                            } else {
                                ArrayList<String> msgList = new ArrayList<String>();
                                msgList.add(new Gson().toJson(message));
                                CompanyMessages companyMessages = new CompanyMessages(msgList,receiverType,receiverId,companyToken.get().getCompany());
                                companyMessagesRepository.save(companyMessages);
                                return msgList;
                            }
                        } else {
                            throw new InvalidReceiverException("Receiver id is invalid");
                        }
                    } else if (Objects.equals(receiverType, "Company")) {
                        Optional<Company> companyReceiver = companyRepository.findById(receiverId);
                        if(companyReceiver.isPresent()) {
                            Optional<CompanyMessages> userSecondary = companyMessagesRepository.findCompanyMessagesByCompany(companyReceiver.get(), receiverId);
                            if(userSecondary.isPresent()) {
                                List<String> list = userSecondary.get().getMessageArray();
                                list.add(new Gson().toJson(message));
                                return list;
                            } else {
                                ArrayList<String> msgList = new ArrayList<String>();
                                msgList.add(new Gson().toJson(message));
                                CompanyMessages companyMessages = new CompanyMessages(msgList,receiverType,receiverId,companyToken.get().getCompany());
                                companyMessagesRepository.save(companyMessages);
                                return msgList;
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
                Optional<CompanyMessages> userPrimary = companyMessagesRepository.findCompanyMessagesByCompany(companyToken.get().getCompany(), receiverId);
                if(userPrimary.isPresent()) {

                } else {
                    if(Objects.equals(receiverType, "User")) {
                        Optional<Users> userReceiver = usersRepository.findById(receiverId);
                        if(userReceiver.isPresent()) {
                            Optional<UserMessages> userSecondary = userMessagesRepository.findUserMessagesByUser(userReceiver.get(), companyToken.get().getCompany().getId());
                            if(userSecondary.isPresent()) {

                            } else {

                            }
                        } else {
                            throw new InvalidReceiverException("Receiver id is invalid");
                        }
                    } else if (Objects.equals(receiverType, "Company")) {
                        Optional<Company> companyReceiver = companyRepository.findById(receiverId);
                        if(companyReceiver.isPresent()) {
                            Optional<CompanyMessages> userSecondary = companyMessagesRepository.findCompanyMessagesByCompany(companyReceiver.get(), receiverId);
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
                Optional<CompanyMessages> userPrimary = companyMessagesRepository.findCompanyMessagesByCompany(companyToken.get().getCompany(), receiverId);
                if(userPrimary.isPresent()) {

                } else {
                    if(Objects.equals(receiverType, "User")) {
                        Optional<Users> userReceiver = usersRepository.findById(receiverId);
                        if(userReceiver.isPresent()) {
                            Optional<UserMessages> userSecondary = userMessagesRepository.findUserMessagesByUser(userReceiver.get(), companyToken.get().getCompany().getId());
                            if(userSecondary.isPresent()) {

                            } else {

                            }
                        } else {
                            throw new InvalidReceiverException("Receiver id is invalid");
                        }
                    } else if (Objects.equals(receiverType, "Company")) {
                        Optional<Company> companyReceiver = companyRepository.findById(receiverId);
                        if(companyReceiver.isPresent()) {
                            Optional<CompanyMessages> userSecondary = companyMessagesRepository.findCompanyMessagesByCompany(companyReceiver.get(), receiverId);
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
