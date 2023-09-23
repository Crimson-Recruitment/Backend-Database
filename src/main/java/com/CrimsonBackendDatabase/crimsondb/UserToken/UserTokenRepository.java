package com.CrimsonBackendDatabase.crimsondb.UserToken;
import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyToken;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    @Query("SELECT s FROM UserToken s WHERE s.accessToken = ?1")
    Optional<UserToken> findUserTokenByToken(String accessToken);
    @Query("SELECT s FROM UserToken s WHERE s.users = ?1")
    Optional<UserToken> findUserTokenByUserId(Users users);
}
