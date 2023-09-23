package com.CrimsonBackendDatabase.crimsondb.UserMessages;

import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table
public class UserMessages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private List<String> messageArray;
    private final String receiverType;
    private final Long receiverId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private final Users user;

    public UserMessages(List<String> messageArray, String receiverType, Users user, Long receiverId) {
        this.messageArray = messageArray;
        this.receiverType = receiverType;
        this.receiverId = receiverId;
        this.user = user;
    }

    public void setMessageArray(List<String> messageArray) {
        this.messageArray = messageArray;
    }

}
