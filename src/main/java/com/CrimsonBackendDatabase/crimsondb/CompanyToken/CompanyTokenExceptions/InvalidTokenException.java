package com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenExceptions;

public class InvalidTokenException extends Exception {
    public InvalidTokenException() {
        super("Token is either Invalid or Expired!");
    }
}
