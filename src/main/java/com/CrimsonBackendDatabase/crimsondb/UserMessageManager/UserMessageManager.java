package com.CrimsonBackendDatabase.crimsondb.UserMessageManager;

import com.CrimsonBackendDatabase.crimsondb.UserChatMessages.UserChatMessages;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Collection;
import java.util.List;

@Getter
@Entity
@Table
public class UserMessageManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String receiverType;
    private Long receiverId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private final Users user;

    @JsonIgnore
    @OneToMany(mappedBy = "userMessageManager", cascade = CascadeType.ALL)
    private Collection<UserChatMessages> userMessages;

    public UserMessageManager(String receiverType, Users user, Long receiverId) {
        this.receiverType = receiverType;
        this.receiverId = receiverId;
        this.user = user;
    }
}
