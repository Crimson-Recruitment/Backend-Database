package com.CrimsonBackendDatabase.crimsondb.UserToken;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenExceptions.InvalidTokenException;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

/**
 * This class is used to interact with the companyToken table.
 */
@Service
public class UserTokenService {
    private final UserTokenRepository userTokenRepository;
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.refreshExpirationDateInMs}")
    private int refreshExpirationDateInMs;

    /**
     * This is used to initialize the companyTokenRepository interface.
     */
    @Autowired
    public UserTokenService(UserTokenRepository userTokenRepository){
        this.userTokenRepository = userTokenRepository;
    }

    /**
     * This function is used to generate a token for the user during registration.
     * @param users is the User object that contains the user details used in generating the token.
     * @return String which is the token that has been generated.
     */
    public String generateToken(Users users) {
        String new_token = Jwts.builder().setSubject(String.valueOf(users.getId())).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationDateInMs))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        userTokenRepository.save(new UserToken(new_token, users));
        return  new_token;


    }

    @Transactional
    public String regenerateToken(Users users) throws InvalidTokenException {
        Optional<UserToken> companyToken = userTokenRepository.findUserTokenByUserId(users);
        if (companyToken.isPresent()) {
            boolean isValid = validateToken(companyToken.get().getAccessToken(), String.valueOf(users.getId()));
            if (isValid) {
                return companyToken.get().getAccessToken();
            } else {
                String new_token = Jwts.builder().setSubject(String.valueOf(users.getId())).setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationDateInMs))
                        .signWith(SignatureAlgorithm.HS512, secret).compact();
                companyToken.get().setAccessToken(new_token);
                return  new_token;
            }
        } else {
            throw  new InvalidTokenException("No Token mapped to this user!");
        }
    }

    /**
     * Is a function that calls the findCompanyTokenUserId function in the companyTokenRepository.
     * @param users contains the user data.
     * @return Returns null or a companyToken associated to the user.
     */
    public Optional<UserToken> findCompanyTokenByCompanyId(Users users) {
        return userTokenRepository.findUserTokenByUserId(users);

    }

    /**
     * This function returns a true or false value depending on where a token is valid or not.
     * @param token is the token to be validated.
     * @param _username is used to compare the subject of the token as a validation method.
     * @return boolean value, true if token is valid and vice versa.
     */
    public boolean validateToken(String token, String _username) {
        try {
            final String username = getClaimFromToken(token, Claims::getSubject);
            final Date expiration = getClaimFromToken(token, Claims::getExpiration);
            return username.equals(_username) && !expiration.before(new Date());
        } catch (Exception e){
            return false;
        }

    }

    /**
     * This function is user to find a companyToken by its token.
     * @param token which is the token used as a parameter for the search.
     * @return Returns null or a CompanyToken if token associates with one.
     */
    public Optional<UserToken> findUserToken(String token) {
        return userTokenRepository.findUserTokenByToken(token);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
