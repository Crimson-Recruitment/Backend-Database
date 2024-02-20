package com.CrimsonBackendDatabase.crimsondb.Meetings;

import com.CrimsonBackendDatabase.crimsondb.UserToken.UserToken;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MeetingsRepository extends JpaRepository<Meetings, Long> {
    @Query("SELECT s FROM Meetings s WHERE s.userId = ?1")
    Optional<List<Meetings>> findMeetingByUser(Long id);
    @Query("SELECT s FROM Meetings s WHERE s.companyId = ?1")
    Optional<List<Meetings>> findMeetingByCompany(Long id);
}
