package com.CrimsonBackendDatabase.crimsondb.Employee;

import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyToken;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenRepository;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import com.CrimsonBackendDatabase.crimsondb.Users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UsersRepository usersRepository;
    private final CompanyTokenRepository tokenRepository;

    public void createEmployee(Long userId, String accessToken) {
        CompanyToken token = tokenRepository.findCompanyTokenByToken(accessToken).orElseThrow();
        Users user = usersRepository.findById(userId).orElseThrow();
        Employee emp = Employee.builder().user(user).company(token.getCompany()).build();
        employeeRepository.save(emp);
    }}
