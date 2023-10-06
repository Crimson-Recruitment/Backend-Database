package com.CrimsonBackendDatabase.crimsondb.CompanyToken;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenExceptions.InvalidTokenException;
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
public class CompanyTokenService {
    private final CompanyTokenRepository companyTokenRepository;
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.refreshExpirationDateInMs}")
    private int refreshExpirationDateInMs;

    /**
     * This is used to initialize the companyTokenRepository interface.
     */
    @Autowired
    public CompanyTokenService(CompanyTokenRepository companyTokenRepository){
        this.companyTokenRepository = companyTokenRepository;
    }

    /**
     * This function is used to generate a token for the user during registration.
     * @param company is the User object that contains the user details used in generating the token.
     * @return String which is the token that has been generated.
     */
    public String generateToken(Company company) {
        String new_token = Jwts.builder().setSubject(String.valueOf(company.getId())).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationDateInMs))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        companyTokenRepository.save(new CompanyToken(new_token, company));
        return  new_token;


    }
    
    @Transactional
    public String regenerateToken(Company company) throws InvalidTokenException {
        Optional<CompanyToken> companyToken = companyTokenRepository.findCompanyTokenByCompanyId(company);
        if (companyToken.isPresent()) {
            boolean isValid = validateToken(companyToken.get().getAccessToken(), String.valueOf(company.getId()));
            if (isValid) {
                return companyToken.get().getAccessToken();
            } else {
                String new_token = Jwts.builder().setSubject(String.valueOf(company.getId())).setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationDateInMs))
                        .signWith(SignatureAlgorithm.HS512, secret).compact();
                companyToken.get().setAccessToken(new_token);
                return  new_token;
            }
        } else {
            throw  new InvalidTokenException();
        }
    }

    /**
     * Is a function that calls the findCompanyTokenUserId function in the companyTokenRepository.
     * @param company contains the user data.
     * @return Returns null or a companyToken associated to the user.
     */
    public Optional<CompanyToken> findCompanyTokenByCompanyId(Company company) {
        return companyTokenRepository.findCompanyTokenByCompanyId(company);

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
    public Optional<CompanyToken> findCompanyToken(String token) {
        return companyTokenRepository.findCompanyTokenByToken(token);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
