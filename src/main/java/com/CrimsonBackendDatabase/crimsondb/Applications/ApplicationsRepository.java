package com.CrimsonBackendDatabase.crimsondb.Applications;

import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationsRepository extends JpaRepository<Applications, Long> {
    @Query("SELECT s FROM Applications s WHERE s.user = ?1")
    Optional<List<Applications>> findApplicationsByUsers(Users user);

}
