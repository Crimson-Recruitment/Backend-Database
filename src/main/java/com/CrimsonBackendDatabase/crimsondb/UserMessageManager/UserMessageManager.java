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
    private List<String> messageArray;
    private final String receiverType;
    private final Long receiverId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private final Users user;

    @JsonIgnore
    @OneToMany(mappedBy = "userMessageManager", cascade = CascadeType.ALL)
    private Collection<UserChatMessages> userMessages;

    public UserMessageManager(List<String> messageArray, String receiverType, Users user, Long receiverId) {
        this.messageArray = messageArray;
        this.receiverType = receiverType;
        this.receiverId = receiverId;
        this.user = user;
    }

    public void setMessageArray(List<String> messageArray) {
        this.messageArray = messageArray;
    }

}
