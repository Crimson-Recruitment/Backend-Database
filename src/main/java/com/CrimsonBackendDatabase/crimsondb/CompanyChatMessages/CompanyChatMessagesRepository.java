package com.CrimsonBackendDatabase.crimsondb.CompanyChatMessages;

import com.CrimsonBackendDatabase.crimsondb.CompanyMessageManager.CompanyMessageManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyChatMessagesRepository extends JpaRepository<CompanyChatMessages, Long> {
    @Modifying
    @Query("DELETE from CompanyChatMessages b WHERE b.companyMessageManager = ?1 AND b.receiverId = ?2")
    public void deleteCompanyChatMessagesByCompanyAndReceiverId(CompanyMessageManager companyMessageManager, Long receiverId);

    @Query("SELECT b from CompanyChatMessages b WHERE b.companyMessageManager = ?1 AND b.receiverId = ?2")
    public Optional<List<CompanyChatMessages>> getCompanyChatMessagesByCompanyAndReceiverId(CompanyMessageManager companyMessageManager, Long receiverId);
}
