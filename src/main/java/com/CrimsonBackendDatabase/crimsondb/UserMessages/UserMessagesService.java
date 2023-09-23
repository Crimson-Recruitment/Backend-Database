package com.CrimsonBackendDatabase.crimsondb.UserMessages;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import com.CrimsonBackendDatabase.crimsondb.Company.CompanyRepository;
import com.CrimsonBackendDatabase.crimsondb.CompanyMessages.CompanyMessages;
import com.CrimsonBackendDatabase.crimsondb.CompanyMessages.CompanyMessagesRepository;
import com.CrimsonBackendDatabase.crimsondb.UserMessages.UserMessagesException.InvalidReceiverException;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserToken;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenService;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import com.CrimsonBackendDatabase.crimsondb.Users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserMessagesService {
    private final UserTokenService userTokenService;
    private final UsersRepository usersRepository;
    private final CompanyRepository companyRepository;
    private final UserMessagesRepository userMessagesRepository;
    private final CompanyMessagesRepository companyMessagesRepository;

    @Autowired
    public UserMessagesService(UserTokenService userTokenService, UsersRepository usersRepository, CompanyRepository companyRepository,UserMessagesRepository userMessagesRepository, CompanyMessagesRepository companyMessagesRepository) {
        this.userTokenService = userTokenService;
        this.usersRepository = usersRepository;
        this.companyRepository = companyRepository;
        this.userMessagesRepository = userMessagesRepository;
        this.companyMessagesRepository = companyMessagesRepository;
    }
    public HashMap<String, String> postUserMessage(String accessToken, String message, Long receiverId, String receiverType) throws InvalidTokenException, InvalidReceiverException {
        Optional<UserToken> userToken = userTokenService.findUserToken(accessToken);
        if(userToken.isPresent()) {
            boolean isValid = userTokenService.validateToken(accessToken, String.valueOf(userToken.get().getUsers().getId()));
            if(isValid) {
                Optional<UserMessages> userPrimary = userMessagesRepository.findUserMessagesByUser(userToken.get().getUsers(), receiverId);
                if(userPrimary.isPresent()) {

                } else {
                    if(Objects.equals(receiverType, "User")) {
                        Optional<Users> userReceiver = usersRepository.findById(receiverId);
                        if(userReceiver.isPresent()) {
                            Optional<UserMessages> userSecondary = userMessagesRepository.findUserMessagesByUser(userReceiver.get(), userToken.get().getUsers().getId());
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
    public HashMap<String, String> clearMessages(String accessToken, Long receiverId, String receiverType) throws InvalidReceiverException, InvalidTokenException {
        Optional<UserToken> userToken = userTokenService.findUserToken(accessToken);
        if(userToken.isPresent()) {
            boolean isValid = userTokenService.validateToken(accessToken, String.valueOf(userToken.get().getUsers().getId()));
            if(isValid) {
                Optional<UserMessages> userPrimary = userMessagesRepository.findUserMessagesByUser(userToken.get().getUsers(), receiverId);
                if(userPrimary.isPresent()) {

                } else {
                    if(Objects.equals(receiverType, "User")) {
                        Optional<Users> userReceiver = usersRepository.findById(receiverId);
                        if(userReceiver.isPresent()) {
                            Optional<UserMessages> userSecondary = userMessagesRepository.findUserMessagesByUser(userReceiver.get(), userToken.get().getUsers().getId());
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
    public HashMap<String, String> deleteMessage(String accessToken, Long receiverId, String receiverType) throws InvalidReceiverException, InvalidTokenException {
        Optional<UserToken> userToken = userTokenService.findUserToken(accessToken);
        if(userToken.isPresent()) {
            boolean isValid = userTokenService.validateToken(accessToken, String.valueOf(userToken.get().getUsers().getId()));
            if(isValid) {
                Optional<UserMessages> userPrimary = userMessagesRepository.findUserMessagesByUser(userToken.get().getUsers(), receiverId);
                if(userPrimary.isPresent()) {

                } else {
                    if(Objects.equals(receiverType, "User")) {
                        Optional<Users> userReceiver = usersRepository.findById(receiverId);
                        if(userReceiver.isPresent()) {
                            Optional<UserMessages> userSecondary = userMessagesRepository.findUserMessagesByUser(userReceiver.get(), userToken.get().getUsers().getId());
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
