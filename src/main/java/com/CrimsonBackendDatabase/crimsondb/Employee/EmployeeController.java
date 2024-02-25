package com.CrimsonBackendDatabase.crimsondb.Employee;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    @GetMapping("/get-info/{id}")
    public Employee getEmployee(@RequestHeader("Authorization") String accessToken, @PathVariable("id") Long employeeId) {
        return employeeService.getEmployee(employeeId, accessToken);
    }

    @PostMapping("/update")
    public HashMap<String, String> updateEmployee(@RequestHeader("Authorization") String accessToken, @RequestBody Employee employee) {
        return employeeService.updateEmployeeDetails(employee, accessToken);
    }
}
