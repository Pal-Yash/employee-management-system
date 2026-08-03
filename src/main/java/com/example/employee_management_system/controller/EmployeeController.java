package com.example.employee_management_system.controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.*;

import com.example.employee_management_system.dto.CreateEmployeeRequestDTO;
import com.example.employee_management_system.dto.CreateEmployeeResponseDTO;
import com.example.employee_management_system.dto.UpdateEmployeeRequestDTO;
import com.example.employee_management_system.dto.UpdateEmployeeResponseDTO;
import com.example.employee_management_system.entity.EmployeeEntity;
import com.example.employee_management_system.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Employee APIs", description = "CRUD Operations for Employees")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }
    @Operation(summary = "Create Employee")
    @PostMapping()
    public ResponseEntity<CreateEmployeeResponseDTO> createEmployee(@Valid @RequestBody CreateEmployeeRequestDTO createEmployeeRequestDTO){
        CreateEmployeeResponseDTO createEmployee=employeeService.createEmployee(createEmployeeRequestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createEmployee);
    }


    @Operation(summary = "Get All Employees")
    @GetMapping()
    public Page<CreateEmployeeResponseDTO> getEmployees(Pageable pageable){

        return employeeService.getAllEmployees(pageable);

    }
    @Operation(summary = "Get Employee By Id")
    @GetMapping("/{id}")
    public ResponseEntity<CreateEmployeeResponseDTO> getEmployee(@PathVariable Long id){
        CreateEmployeeResponseDTO createEmployeeResponseDTO=employeeService.getEmployee(id);


        return ResponseEntity.ok(createEmployeeResponseDTO);
    }

    @Operation(summary = "Get Employee By Email")
    @GetMapping("/email/{email}")
    public CreateEmployeeResponseDTO getEmployeeByEmail(
            @PathVariable String email){

        return employeeService.getEmployeeByEmail(email);

    }

    @Operation(summary = "Get Employees By Department")
    @GetMapping("/department/{department}")
    public List<CreateEmployeeResponseDTO> getEmployeeByDepartment(
            @PathVariable String department){

        return employeeService.getEmployeeByDepartment(department);

    }

    @Operation(summary = "Search Employee By Name")
    @GetMapping("/name/{name}")
    public List<CreateEmployeeResponseDTO> getEmployeeByName(
            @PathVariable String name){

        return employeeService.getEmployeeByName(name);

    }

    @Operation(summary = "Update Employee")
    @PutMapping("/{id}")
    public ResponseEntity<UpdateEmployeeResponseDTO> updateEmployee(@PathVariable Long id,@Valid @RequestBody UpdateEmployeeRequestDTO updateEmployeeRequestDTO){
        UpdateEmployeeResponseDTO employeeResponseDTO=employeeService.updateEmployee(id, updateEmployeeRequestDTO);

        return ResponseEntity.ok(employeeResponseDTO);
    }
    @Operation(summary = "Soft Delete Employee")

    @PatchMapping("/{id}/soft-delete")
    public ResponseEntity<String> softDelete(@PathVariable Long id){
        employeeService.softDelete(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Delete Employee")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
