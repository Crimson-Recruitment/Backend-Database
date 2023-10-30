package com.CrimsonBackendDatabase.crimsondb.Notifcations;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Notifications {
    private String title;
    private Object message;
}
