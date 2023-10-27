package com.CrimsonBackendDatabase.crimsondb.UserChatMessages;

import com.CrimsonBackendDatabase.crimsondb.Company.Company;
import com.CrimsonBackendDatabase.crimsondb.UserMessageManager.UserMessageManager;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table
@NoArgsConstructor
public class UserChatMessages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Long receiverId;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "msgmanager_id", referencedColumnName = "id")
    private UserMessageManager userMessageManager;

    public UserChatMessages(String message,Long receiverId, UserMessageManager userMessageManager) {
        this.message = message;
        this.receiverId = receiverId;
        this.userMessageManager = userMessageManager;
    }
}
