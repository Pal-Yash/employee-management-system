package com.example.employee_management_system.controller;

import com.example.employee_management_system.entity.EmployeeEntity;
import com.example.employee_management_system.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }
    @PostMapping("/create")
    public ResponseEntity<EmployeeEntity> createEmployee(@RequestBody  EmployeeEntity employeeEntity){
        EmployeeEntity createEmployee=employeeService.createEmployee(employeeEntity);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createEmployee);
    }
}
