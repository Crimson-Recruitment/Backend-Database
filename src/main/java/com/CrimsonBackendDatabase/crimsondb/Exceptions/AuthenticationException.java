package com.CrimsonBackendDatabase.crimsondb.Exceptions;

public class AuthenticationException extends Exception{
    public AuthenticationException() {
        super("Invalid email or password");
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
