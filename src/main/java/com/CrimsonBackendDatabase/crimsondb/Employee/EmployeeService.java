package com.CrimsonBackendDatabase.crimsondb.Employee;

import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyToken;
import com.CrimsonBackendDatabase.crimsondb.CompanyToken.CompanyTokenRepository;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import com.CrimsonBackendDatabase.crimsondb.Users.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UsersRepository usersRepository;
    private final CompanyTokenRepository tokenRepository;

    public void createEmployee(Long userId, String accessToken, String position) {
        CompanyToken token = tokenRepository.findCompanyTokenByToken(accessToken).orElseThrow();
        Users user = usersRepository.findById(userId).orElseThrow();
        Employee emp = Employee.builder().user(user).startDate(new Date()).position(position).company(token.getCompany()).build();
        employeeRepository.save(emp);
    }

    public Employee getEmployee(Long employeeId, String accessToken) {
        CompanyToken token = tokenRepository.findCompanyTokenByToken(accessToken).orElseThrow();
       return employeeRepository.findById(employeeId).orElseThrow();
    }

    @Transactional
    public HashMap<String, String> updateEmployeeDetails(Employee newEmployee, String accessToken) {
        CompanyToken token = tokenRepository.findCompanyTokenByToken(accessToken).orElseThrow();
        Employee emp = employeeRepository.findById(newEmployee.getId()).orElseThrow();
        emp.setAddress(newEmployee.getAddress());
        emp.setDateOfBirth(newEmployee.getDateOfBirth());
        emp.setDepartment(newEmployee.getDepartment());
        emp.setGender(newEmployee.getGender());
        emp.setPosition(newEmployee.getPosition());
        emp.setStartDate(newEmployee.getStartDate());
        emp.setCriminalRecord(newEmployee.getCriminalRecord());
        emp.setMedicalReport(newEmployee.getMedicalReport());
        emp.setNationalIdFront(newEmployee.getNationalIdFront());
        emp.setNationalIdBack(newEmployee.getNationalIdBack());
        emp.setPassport(newEmployee.getPassport());
        emp.setNextOfKinName(newEmployee.getNextOfKinName());
        emp.setNextOfKinRelationship(newEmployee.getNextOfKinRelationship());
        emp.setNextOfKinPhone(newEmployee.getNextOfKinPhone());
        emp.setNextOfKinAddress(newEmployee.getNextOfKinAddress());
        employeeRepository.save(emp);
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("result", "success");
        return data;
    }

}

