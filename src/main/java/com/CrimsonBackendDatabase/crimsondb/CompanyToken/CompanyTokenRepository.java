package com.CrimsonBackendDatabase.crimsondb.CompanyToken;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyTokenRepository extends JpaRepository<CompanyToken, Long> {
    @Query("SELECT s FROM CompanyToken s WHERE s.accessToken = ?1")
    Optional<CompanyToken> findCompanyTokenByToken(String accessToken);
    @Query("SELECT s FROM CompanyToken s WHERE s.company = ?1")
    Optional<CompanyToken> findCompanyTokenByCompanyId(Company company);
}
