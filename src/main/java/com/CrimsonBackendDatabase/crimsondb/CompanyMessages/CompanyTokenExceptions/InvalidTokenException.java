package com.CrimsonBackendDatabase.crimsondb.CompanyMessages.CompanyTokenExceptions;

public class InvalidTokenException extends Exception{
    public InvalidTokenException() {
        super("Token is invalid or expired!");
    }
}
