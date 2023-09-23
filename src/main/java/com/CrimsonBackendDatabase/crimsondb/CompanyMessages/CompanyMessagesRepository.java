package com.CrimsonBackendDatabase.crimsondb.CompanyMessages;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import com.CrimsonBackendDatabase.crimsondb.UserMessages.UserMessages;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyMessagesRepository extends JpaRepository<CompanyMessages,Long> {
    @Query("SELECT s FROM CompanyMessages s WHERE s.company = ?1 and s.receiverId = ?2")
    Optional<CompanyMessages> findCompanyMessagesByCompany(Company company, Long receiverId);
}
