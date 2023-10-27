package com.CrimsonBackendDatabase.crimsondb.UserChatMessages;


import com.CrimsonBackendDatabase.crimsondb.UserMessageManager.UserMessageManager;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserChatMessagesRepository extends JpaRepository<UserChatMessages, Long> {
    @Modifying
    @Query("DELETE from UserChatMessages b WHERE b.userMessageManager = ?1 AND b.receiverId = ?2")
    public void deleteUserChatMessagesByUserAndReceiverId(UserMessageManager userMessageManager, Long receiverId);

    @Query("SELECT b from UserChatMessages b WHERE b.userMessageManager = ?1 AND b.receiverId = ?2")
    public Optional<List<UserChatMessages>> getUserChatMessagesByUserAndReceiverId(UserMessageManager userMessageManager, Long receiverId);
}
