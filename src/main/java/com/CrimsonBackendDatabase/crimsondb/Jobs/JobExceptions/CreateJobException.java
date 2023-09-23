package com.CrimsonBackendDatabase.crimsondb.Jobs.JobExceptions;

public class CreateJobException extends Exception{
    public CreateJobException() {
        super("Job is either expired or invalid");
    }
}
