package com.CrimsonBackendDatabase.crimsondb.Users;

import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    @Query("SELECT s FROM Users s WHERE s.email = ?1")
    Optional<Users> findUsersByEmail(String email);

    @Modifying
    @Query("update Users s set s.paymentRandom = ?1 where s.id = ?2")
    void setUserInfoById(String random, Long id);
}
