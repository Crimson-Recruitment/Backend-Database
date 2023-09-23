package com.CrimsonBackendDatabase.crimsondb.UserToken.UserTokenExceptions;

public class InvalidTokenException extends Exception{
    public InvalidTokenException() {
        super("Token is invalid or expired!");
    }
}
