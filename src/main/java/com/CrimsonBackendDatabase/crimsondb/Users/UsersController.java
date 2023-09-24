package com.CrimsonBackendDatabase.crimsondb.Users;

import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.Exceptions.AuthenticationException;
import com.CrimsonBackendDatabase.crimsondb.Exceptions.EmailAlreadyExistsException;
import com.CrimsonBackendDatabase.crimsondb.Users.UsersException.InvalidUserException;
import com.CrimsonBackendDatabase.crimsondb.Utils.LoginDetails;
import com.CrimsonBackendDatabase.crimsondb.Utils.PasswordChange;
import com.CrimsonBackendDatabase.crimsondb.Utils.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
@RequestMapping("v1/user")
public class UsersController {

    private final UsersService userService;

    @Autowired
    public UsersController(UsersService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public HashMap<String, String> userRegister(@RequestBody Users user) {
        try {
            return userService.userRegister(user);
        } catch (EmailAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public  HashMap<String,String> userLogin(@RequestBody LoginDetails loginDetails) {
        try {
            return userService.userLogin(loginDetails.getEmail(),loginDetails.getPassword());
        } catch (InvalidTokenException | AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/user-details")
    public Users getUserDetails(@RequestHeader("Authorization") String accessToken) {
        try {
            return  userService.getUserDetails(accessToken);
        } catch (com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/user-info/{id}")
    public UserDetails getUserInfo(@PathVariable("id") Long id) {
        try {
            return userService.getUserInfo(id);
        } catch (InvalidUserException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/update")
    public HashMap<String, String> updateUserDetails(@RequestHeader("Authorization") String accessToken,@RequestBody Users user) {
        try {
            return userService.updateUserDetails(accessToken,user);
        } catch (com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("change-password")
    public HashMap<String, String> changePassword(@RequestHeader("Authorization") String accessToken, @RequestBody PasswordChange passwordChange) {
        try {
            return  userService.changePassword(accessToken,passwordChange);
        } catch (AuthenticationException |
                 com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/validate-email")
    public HashMap<String, String> validateEmail(@RequestHeader("Authorization") String accessToken) {
        try {
            return userService.validateEmail(accessToken);
        } catch (com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/validate-phone-number")
    public  HashMap<String, String> validatePhoneNumber(@RequestHeader("Authorization") String accessToken) {
        try {
            return userService.validatePassword(accessToken);
        } catch (com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions.InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }
}
