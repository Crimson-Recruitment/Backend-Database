package com.CrimsonBackendDatabase.crimsondb.Company;

import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT s FROM Company s WHERE s.email = ?1")
    Optional<Company> findCompanyByEmail(String email);

    @Modifying
    @Query("update Company s set s.paymentRandom = ?1 where s.id = ?2")
    void setCompanyInfoById(String random, Long id);
}
