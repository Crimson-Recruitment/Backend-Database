package com.CrimsonBackendDatabase.crimsondb.CompanyToken;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyTokenRepository extends JpaRepository<Company, Long> {
}
