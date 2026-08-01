package com.example.employee_management_system.service;

import com.example.employee_management_system.entity.EmployeeEntity;
import com.example.employee_management_system.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepositry;
    public EmployeeService(EmployeeRepository employeeRepositry){
        this.employeeRepositry=employeeRepositry;
    }
    public EmployeeEntity createEmployee(EmployeeEntity employeeEntity){
           employeeEntity.setDeleted(false);
           EmployeeEntity employeeEntityResp=employeeRepositry.save(employeeEntity);
           return employeeEntityResp;
    }

}
