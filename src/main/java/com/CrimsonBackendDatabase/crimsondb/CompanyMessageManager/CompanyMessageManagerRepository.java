package com.CrimsonBackendDatabase.crimsondb.CompanyMessageManager;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyMessageManagerRepository extends JpaRepository<CompanyMessageManager,Long> {
    @Query("SELECT s FROM CompanyMessageManager s WHERE s.company = ?1 and s.receiverId = ?2")
    Optional<CompanyMessageManager> findCompanyMessageManagerByCompany(Company company, Long receiverId);
}
