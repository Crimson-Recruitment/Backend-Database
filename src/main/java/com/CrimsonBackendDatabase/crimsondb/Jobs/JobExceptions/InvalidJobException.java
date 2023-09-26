package com.CrimsonBackendDatabase.crimsondb.Jobs.JobExceptions;

public class InvalidJobException extends Exception{
    public InvalidJobException() {
        super("Job is either expired or invalid");
    }
}
