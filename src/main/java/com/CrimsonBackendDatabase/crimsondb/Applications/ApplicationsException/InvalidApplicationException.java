package com.CrimsonBackendDatabase.crimsondb.Applications.ApplicationsException;

public class InvalidApplicationException extends Exception{
    public InvalidApplicationException() {
        super("Application is invalid!");
    }

    public InvalidApplicationException(String message) {
        super(message);
    }
}
