package com.example.employee_management_system.controller;
import java.util.*;

import com.example.employee_management_system.dto.CreateEmployeeRequestDTO;
import com.example.employee_management_system.dto.CreateEmployeeResponseDTO;
import com.example.employee_management_system.dto.UpdateEmployeeRequestDTO;
import com.example.employee_management_system.dto.UpdateEmployeeResponseDTO;
import com.example.employee_management_system.entity.EmployeeEntity;
import com.example.employee_management_system.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }
    @PostMapping()
    public ResponseEntity<CreateEmployeeResponseDTO> createEmployee(@Valid @RequestBody CreateEmployeeRequestDTO createEmployeeRequestDTO){
        CreateEmployeeResponseDTO createEmployee=employeeService.createEmployee(createEmployeeRequestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createEmployee);
    }
    @GetMapping()
    public ResponseEntity<List<CreateEmployeeResponseDTO>> getAllEmployee(){
           List<CreateEmployeeResponseDTO> createEmployeeResponseDTOList=employeeService.getAllEmployee();
           if(createEmployeeResponseDTOList.isEmpty()){
               return ResponseEntity.notFound().build();
           }
           return ResponseEntity.ok(createEmployeeResponseDTOList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CreateEmployeeResponseDTO> getEmployee(@PathVariable Long id){
        CreateEmployeeResponseDTO createEmployeeResponseDTO=employeeService.getEmployee(id);

        if(createEmployeeResponseDTO==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(createEmployeeResponseDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UpdateEmployeeResponseDTO> updateEmployee(@PathVariable Long id,@Valid @RequestBody UpdateEmployeeRequestDTO updateEmployeeRequestDTO){
        UpdateEmployeeResponseDTO employeeResponseDTO=employeeService.updateEmployee(id, updateEmployeeRequestDTO);
        if(employeeResponseDTO==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employeeResponseDTO);
    }
    @PatchMapping("/{id}/soft-delete")
    public ResponseEntity<String> softDelete(@PathVariable Long id){
        Boolean employeeEntity=employeeService.softDelete(id);
        if(!employeeEntity){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Soft delete success");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Boolean employeeEntity=employeeService.delete(id);
        if(!employeeEntity){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Delete successfull");
    }
}
