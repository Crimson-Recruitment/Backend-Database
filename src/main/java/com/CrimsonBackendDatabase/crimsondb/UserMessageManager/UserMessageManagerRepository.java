package com.CrimsonBackendDatabase.crimsondb.UserMessageManager;

import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMessageManagerRepository extends JpaRepository<UserMessageManager, Long> {
    @Query("SELECT s FROM UserMessageManager s WHERE s.user = ?1 and s.receiverId = ?2")
    Optional<UserMessageManager> findUserMessageManagerByUser(Users user, Long receiverId);
}
