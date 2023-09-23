package com.CrimsonBackendDatabase.crimsondb.UserMessages;

import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMessagesRepository extends JpaRepository<UserMessages, Long> {
    @Query("SELECT s FROM UserMessages s WHERE s.user = ?1 and s.receiverId = ?2")
    Optional<UserMessages> findUserMessagesByUser(Users user, Long receiverId);
}
