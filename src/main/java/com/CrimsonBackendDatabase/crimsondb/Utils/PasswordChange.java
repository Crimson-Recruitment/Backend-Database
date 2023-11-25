package com.CrimsonBackendDatabase.crimsondb.Utils;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordChange {
    private String newPassword;
    private String oldPassword;
}
