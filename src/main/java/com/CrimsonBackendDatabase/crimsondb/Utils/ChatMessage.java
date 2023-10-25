package com.CrimsonBackendDatabase.crimsondb.Utils;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    int id;
    String message;
}
