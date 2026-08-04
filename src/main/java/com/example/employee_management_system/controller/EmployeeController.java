package com.example.employee_management_system.controller;

import com.example.employee_management_system.dto.CreateEmployeeRequestDTO;
import com.example.employee_management_system.dto.CreateEmployeeResponseDTO;
import com.example.employee_management_system.dto.UpdateEmployeeRequestDTO;
import com.example.employee_management_system.dto.UpdateEmployeeResponseDTO;
import com.example.employee_management_system.response.ApiResponse;
import com.example.employee_management_system.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Employee APIs", description = "CRUD Operations for Employees")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Create Employee")
    @PostMapping
    public ResponseEntity<ApiResponse<CreateEmployeeResponseDTO>> createEmployee(
            @Valid @RequestBody CreateEmployeeRequestDTO dto) {

        CreateEmployeeResponseDTO employee =
                employeeService.createEmployee(dto);

        ApiResponse<CreateEmployeeResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "Employee created successfully",
                        employee
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @Operation(summary = "Get All Employees")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CreateEmployeeResponseDTO>>> getAllEmployee(
            Pageable pageable) {

        Page<CreateEmployeeResponseDTO> employees =
                employeeService.getAllEmployees(pageable);

        ApiResponse<Page<CreateEmployeeResponseDTO>> response =
                new ApiResponse<>(
                        true,
                        "Employees fetched successfully",
                        employees
                );

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get Employee By Id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CreateEmployeeResponseDTO>> getEmployee(@PathVariable Long id) {

        CreateEmployeeResponseDTO employee = employeeService.getEmployee(id);

        ApiResponse<CreateEmployeeResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "Employee fetched successfully",
                        employee
                );

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get Employee By Email")
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<CreateEmployeeResponseDTO>> getEmployeeByEmail(
            @PathVariable String email) {
        CreateEmployeeResponseDTO employee = employeeService.getEmployeeByEmail(email);
        ApiResponse<CreateEmployeeResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "Employee fetched successfully",
                        employee
                );

        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Get Employees By Department")
    @GetMapping("/department/{department}")
    public ResponseEntity<ApiResponse<Page<CreateEmployeeResponseDTO>>> getEmployeeByDepartment(Pageable pageable,
                                                                                                @PathVariable String department) {
        Page<CreateEmployeeResponseDTO> employees =
                employeeService.getEmployeeByDepartment(pageable, department);

        ApiResponse<Page<CreateEmployeeResponseDTO>> response =
                new ApiResponse<>(
                        true,
                        "Employees fetched successfully",
                        employees
                );

        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Search Employee By Name")
    @GetMapping("/name/{name}")

    public ResponseEntity<ApiResponse<List<CreateEmployeeResponseDTO>>> getEmployeeByName(
            @PathVariable String name) {

        List<CreateEmployeeResponseDTO> employees =
                employeeService.getEmployeeByName(name);

        ApiResponse<List<CreateEmployeeResponseDTO>> response =
                new ApiResponse<>(
                        true,
                        "Employees fetched successfully",
                        employees
                );

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update Employee")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateEmployeeResponseDTO>> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEmployeeRequestDTO dto) {

        UpdateEmployeeResponseDTO employee =
                employeeService.updateEmployee(id, dto);

        ApiResponse<UpdateEmployeeResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "Employee updated successfully",
                        employee
                );

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Soft Delete Employee")
    @DeleteMapping("/soft/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {

        employeeService.softDelete(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete Employee")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }
}
