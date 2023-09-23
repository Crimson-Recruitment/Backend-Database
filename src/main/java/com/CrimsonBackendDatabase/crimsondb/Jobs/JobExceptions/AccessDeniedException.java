package com.CrimsonBackendDatabase.crimsondb.Jobs.JobExceptions;

public class AccessDeniedException extends Exception{
    public AccessDeniedException() {
        super("Access denied to update status of this job!");
    }
}
