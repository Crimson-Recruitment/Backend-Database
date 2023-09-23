package com.CrimsonBackendDatabase.crimsondb.Exceptions;

public class EmailAlreadyExistsException extends Exception{
    public EmailAlreadyExistsException() {
        super("This email already exists!");
    }
}
