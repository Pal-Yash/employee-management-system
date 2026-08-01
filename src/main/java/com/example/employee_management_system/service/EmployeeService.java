package com.example.employee_management_system.service;

import com.example.employee_management_system.dto.CreateEmployeeRequestDTO;
import com.example.employee_management_system.dto.CreateEmployeeResponseDTO;
import com.example.employee_management_system.dto.UpdateEmployeeRequestDTO;
import com.example.employee_management_system.dto.UpdateEmployeeResponseDTO;
import com.example.employee_management_system.entity.EmployeeEntity;
import com.example.employee_management_system.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepositry;
    public EmployeeService(EmployeeRepository employeeRepositry){
        this.employeeRepositry=employeeRepositry;
    }
    public CreateEmployeeResponseDTO createEmployee(CreateEmployeeRequestDTO createEmployeeRequestDTO){
        EmployeeEntity employeeEntity= mapToCreateEntity(createEmployeeRequestDTO);
        employeeEntity.setUpdatedAt(LocalDateTime.now());
        employeeEntity.setCreatedAt(LocalDateTime.now());
        employeeRepositry.save(employeeEntity);
        return mapToCreateDTO(employeeEntity);
    }
    public List<CreateEmployeeResponseDTO> getAllEmployee(){
        List<EmployeeEntity> employeeEntityList=employeeRepositry.findByDeletedIsFalse();

        return employeeEntityList.stream()
                .map(this::mapToCreateDTO)
                .toList();
    }
    public CreateEmployeeResponseDTO getEmployee(Long id){
        Optional<EmployeeEntity> employeeEntity=employeeRepositry.findByIdAndDeletedIsFalse(id);
        if(employeeEntity.isPresent()){
           return mapToCreateDTO(employeeEntity.get());
        }
        return null;
    }
    public UpdateEmployeeResponseDTO updateEmployee(Long id, UpdateEmployeeRequestDTO updateEmployeeRequestDTO){

        Optional<EmployeeEntity> employeeEntity=employeeRepositry.findByIdAndDeletedIsFalse(id);
        if(!employeeEntity.isPresent()){
            return null;
        }
        EmployeeEntity employeeToSave=employeeEntity.get();

        employeeToSave.setEmail(updateEmployeeRequestDTO.getEmail());
        employeeToSave.setName(updateEmployeeRequestDTO.getName());
        employeeToSave.setUpdatedAt(LocalDateTime.now());
        employeeRepositry.save(employeeToSave);

        return mapToUpdateDTO(employeeToSave);
    }
    public Boolean softDelete(Long id){
        Optional<EmployeeEntity>employeeEntity=employeeRepositry.findByIdAndDeletedIsFalse(id);
        if(employeeEntity.isPresent()){
            EmployeeEntity employee=employeeEntity.get();
            employee.setDeleted(true);
            employeeRepositry.save(employee);
            return true;
        }
        return false;
    }
    public Boolean delete(Long id){
        Optional<EmployeeEntity> employeeEntity=employeeRepositry.findByIdAndDeletedIsFalse(id);
        if(employeeEntity.isPresent()){
            employeeRepositry.deleteById(id);
            return true;
        }
        return false;
    }

    private EmployeeEntity mapToCreateEntity(CreateEmployeeRequestDTO createEmployeeRequestDTO){
        EmployeeEntity employeeEntity=new EmployeeEntity();
        employeeEntity.setSalary(createEmployeeRequestDTO.getSalary());
        employeeEntity.setName(createEmployeeRequestDTO.getName());
        employeeEntity.setDepartment(createEmployeeRequestDTO.getDepartment());
        employeeEntity.setEmail(createEmployeeRequestDTO.getEmail());
        employeeEntity.setDeleted(false);

        return employeeEntity;
    }

    private CreateEmployeeResponseDTO mapToCreateDTO(EmployeeEntity employeeEntity){
        CreateEmployeeResponseDTO createEmployeeResponseDTO =new CreateEmployeeResponseDTO();
        createEmployeeResponseDTO.setDepartment(employeeEntity.getDepartment());
        createEmployeeResponseDTO.setEmail(employeeEntity.getEmail());

        createEmployeeResponseDTO.setName(employeeEntity.getName());
        createEmployeeResponseDTO.setSalary(employeeEntity.getSalary());
        createEmployeeResponseDTO.setId(employeeEntity.getId());
        createEmployeeResponseDTO.setCreatedAt(employeeEntity.getCreatedAt());
        createEmployeeResponseDTO.setUpdatedAt(LocalDateTime.now());
        createEmployeeResponseDTO.setMessage("Employee saved successfully");
        return createEmployeeResponseDTO;
    }



    private UpdateEmployeeResponseDTO mapToUpdateDTO(EmployeeEntity employeeEntity){
        UpdateEmployeeResponseDTO updateEmployeeResponseDTO=new UpdateEmployeeResponseDTO();
        updateEmployeeResponseDTO.setDepartment(employeeEntity.getDepartment());
        updateEmployeeResponseDTO.setEmail(employeeEntity.getEmail());

        updateEmployeeResponseDTO.setName(employeeEntity.getName());
        updateEmployeeResponseDTO.setSalary(employeeEntity.getSalary());
        updateEmployeeResponseDTO.setId(employeeEntity.getId());

        updateEmployeeResponseDTO.setUpdatedAt(employeeEntity.getUpdatedAt());
        updateEmployeeResponseDTO.setMessage("Employee details updated successfully");
        return updateEmployeeResponseDTO;
    }
}
