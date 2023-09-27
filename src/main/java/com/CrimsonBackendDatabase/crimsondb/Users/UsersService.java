package com.CrimsonBackendDatabase.crimsondb.Users;

import com.CrimsonBackendDatabase.crimsondb.UserToken.UserToken;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenService;
import com.CrimsonBackendDatabase.crimsondb.Exceptions.AuthenticationException;
import com.CrimsonBackendDatabase.crimsondb.Exceptions.EmailAlreadyExistsException;
import com.CrimsonBackendDatabase.crimsondb.Users.UsersException.InvalidUserException;
import com.CrimsonBackendDatabase.crimsondb.Utils.PasswordChange;
import com.CrimsonBackendDatabase.crimsondb.Utils.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    @Autowired
    private UserTokenService userTokenService;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    @Transactional
    public HashMap<String, String> userRegister(Users user) throws EmailAlreadyExistsException {
        Optional<Users> checkUser = usersRepository.findUsersByEmail(user.getEmail());
        if(checkUser.isPresent()) {
            throw new EmailAlreadyExistsException();
        } else {
            user.setPassword(encoder.encode(user.getPassword()));
            usersRepository.save(user);
            String accessToken = userTokenService.generateToken(user);
            HashMap<String, String> result = new HashMap<String, String>();
            result.put("result", "success");
            result.put("accessToken", accessToken);
            return result;
        }
    };
    @Transactional
    public HashMap<String, String> userLogin(String email, String password) throws com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenExceptions.InvalidTokenException, AuthenticationException {
        Optional<Users> user = usersRepository.findUsersByEmail(email);
        if (user.isPresent()) {
            boolean val =  encoder.matches(password,user.get().getPassword());
            if(val){
                String accessToken = userTokenService.regenerateToken(user.get());
                HashMap<String, String> result = new HashMap<String, String>();
                result.put("result","success");
                result.put("accessToken", accessToken);
                return result;
            } else {
                throw new AuthenticationException();
            }
        } else {
            throw new AuthenticationException();
        }

    };

    @Transactional
    public Users getUserDetails(String accessToken) throws InvalidTokenException {
        Optional<UserToken> userToken = userTokenService.findUserToken(accessToken);
        if(userToken.isPresent()) {
            boolean isValid = userTokenService.validateToken(accessToken, String.valueOf(userToken.get().getUsers().getId()));
            if(isValid) {
                return userToken.get().getUsers();
            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw new InvalidTokenException();
        }
    }

    public UserDetails getUserInfo(Long id) throws InvalidUserException {
        Optional<Users> user = usersRepository.findById(id);
        if (user.isPresent()) {
            return new UserDetails(user.get().getFirstName(), user.get().getLastName(),user.get().getProfileImage(), user.get().getPhoneNumber(), user.get().getEmail(), user.get().getLocation(),user.get().getCv());
        } else {
            throw new InvalidUserException();
        }
    }

    @Transactional
    public HashMap<String, String> updateUserDetails(String accessToken, Users newUser) throws InvalidTokenException {
        Optional<UserToken> userToken = userTokenService.findUserToken(accessToken);
        if(userToken.isPresent()) {
            boolean isValid = userTokenService.validateToken(accessToken, String.valueOf(userToken.get().getUsers().getId()));
            if(isValid) {
                Optional<Users> user = usersRepository.findById(userToken.get().getUsers().getId());
                user.get().setFirstName(newUser.getFirstName());
                user.get().setLastName(newUser.getLastName());
                user.get().setBio(newUser.getBio());
                user.get().setJobTitle(newUser.getJobTitle());
                user.get().setEmail(newUser.getEmail());
                user.get().setLocation(newUser.getLocation());
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
    public HashMap<String, String> changePassword(String accessToken, PasswordChange passwordChange) throws AuthenticationException, InvalidTokenException {
        Optional<UserToken> session = userTokenService.findUserToken(accessToken);
        if(session.isPresent()) {
            boolean isValid = userTokenService.validateToken(accessToken, String.valueOf(session.get().getUsers().getId()));
            if(isValid) {
                Users users = session.get().getUsers();
                boolean val =  encoder.matches(
                        passwordChange.getOldPassword(),
                        users.getPassword()
                );
                if (val) {
                    users.setPassword(encoder.encode(passwordChange.getNewPassword()));
                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put("result", "success");
                    return data;
                }else {
                    throw new AuthenticationException("Incorrect old password");
                }

            } else {
                throw new InvalidTokenException();
            }
        } else {
            throw new InvalidTokenException();
        }
    }

    @Transactional
    public HashMap<String, String> validateEmail(String accessToken) throws InvalidTokenException {
        Optional<UserToken> session = userTokenService.findUserToken(accessToken);
        if(session.isPresent()) {
            boolean isValid = userTokenService.validateToken(accessToken, String.valueOf(session.get().getUsers().getId()));
            if(isValid) {
                Users users = session.get().getUsers();
                users.setEmailValid(true);
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

    public HashMap<String, String> validatePassword(String accessToken) throws InvalidTokenException {
        Optional<UserToken> session = userTokenService.findUserToken(accessToken);
        if(session.isPresent()) {
            boolean isValid = userTokenService.validateToken(accessToken, String.valueOf(session.get().getUsers().getId()));
            if(isValid) {
                Users users = session.get().getUsers();
                users.setPhoneNumberValid(true);
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
