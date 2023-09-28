package com.CrimsonBackendDatabase.crimsondb.Utils;

import lombok.Getter;

@Getter
public class PasswordChange {
    private String newPassword;

    public PasswordChange() {
    }
    public PasswordChange(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
