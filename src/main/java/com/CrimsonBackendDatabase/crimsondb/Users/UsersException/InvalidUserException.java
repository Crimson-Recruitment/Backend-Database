package com.CrimsonBackendDatabase.crimsondb.Users.UsersException;

public class InvalidUserException extends Exception{
    public InvalidUserException() {
        super("User doesn't exist!");
    }
}
