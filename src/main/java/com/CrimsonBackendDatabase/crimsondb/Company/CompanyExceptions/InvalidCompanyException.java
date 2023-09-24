package com.CrimsonBackendDatabase.crimsondb.Company.CompanyExceptions;

public class InvalidCompanyException extends Exception{
    public InvalidCompanyException() {
        super("Company doesn't exist!");
    }
}
