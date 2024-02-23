package com.CrimsonBackendDatabase.crimsondb.Jobs;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobsRepository extends JpaRepository<Jobs, Long> {
    @Query("SELECT s FROM Jobs s WHERE s.company = ?1")
    Optional<List<Jobs>> findJobsByCompany(Company company);
    @Query("SELECT s FROM Jobs s WHERE s.company = null")
    Optional<List<Jobs>> findAdminJobs();

    @Query("SELECT s FROM Jobs s WHERE s.field = ?1")
    Optional<List<Jobs>> findJobsByField(String field);
}
