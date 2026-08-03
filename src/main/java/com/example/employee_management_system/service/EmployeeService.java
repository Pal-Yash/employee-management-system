package com.example.employee_management_system.service;

import com.example.employee_management_system.dto.CreateEmployeeRequestDTO;
import com.example.employee_management_system.dto.CreateEmployeeResponseDTO;
import com.example.employee_management_system.dto.UpdateEmployeeRequestDTO;
import com.example.employee_management_system.dto.UpdateEmployeeResponseDTO;
import com.example.employee_management_system.entity.EmployeeEntity;
import com.example.employee_management_system.exception.DuplicateResourceException;
import com.example.employee_management_system.exception.ResourceNotFoundException;
import com.example.employee_management_system.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import java.util.List;


@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository=employeeRepository;
    }
    public CreateEmployeeResponseDTO createEmployee(CreateEmployeeRequestDTO createEmployeeRequestDTO){
        EmployeeEntity employeeEntity= mapToCreateEntity(createEmployeeRequestDTO);
        if(emailExists(employeeEntity)){
            throw new DuplicateResourceException("Employee with "+employeeEntity.getEmail()+" already exists");
        }
        employeeRepository.save(employeeEntity);
        return mapToCreateDTO(employeeEntity);
    }

    public CreateEmployeeResponseDTO getEmployee(Long id){
        EmployeeEntity employeeEntityResp=employeeRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new ResourceNotFoundException("Employee with id "+ id + " not found"));
        return mapToCreateDTO(employeeEntityResp);
    }
    public UpdateEmployeeResponseDTO updateEmployee(Long id, UpdateEmployeeRequestDTO updateEmployeeRequestDTO){

        EmployeeEntity employeeEntity=employeeRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new ResourceNotFoundException("Employee with id "+ id + " not found"));

        if(!employeeEntity.getEmail().equals(updateEmployeeRequestDTO.getEmail())
                && employeeRepository.existsByEmail(updateEmployeeRequestDTO.getEmail())){
            throw new DuplicateResourceException("Employee already exists");
        }

        employeeEntity.setEmail(updateEmployeeRequestDTO.getEmail());
        employeeEntity.setName(updateEmployeeRequestDTO.getName());
        employeeEntity.setUpdatedAt(LocalDateTime.now());
        employeeRepository.save(employeeEntity);

        return mapToUpdateDTO(employeeEntity);
    }
    public void deleteEmployee(Long id){
        EmployeeEntity employeeEntity=employeeRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new ResourceNotFoundException("Employee with id "+ id + " not found"));;
        employeeRepository.delete(employeeEntity);
    }
    public void softDelete(Long id){
        EmployeeEntity employeeEntity = employeeRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " not found"));

        employeeEntity.setDeleted(true);
        employeeRepository.save(employeeEntity);
    }

    public Page<CreateEmployeeResponseDTO> getAllEmployees(Pageable pageable){

        Page<EmployeeEntity> employeePage =
                employeeRepository.findByDeletedIsFalse(pageable);

        return employeePage.map(this::mapToCreateDTO);

    }

    public CreateEmployeeResponseDTO getEmployeeByEmail(String email){

        EmployeeEntity employeeEntity = employeeRepository
                .findByEmailAndDeletedIsFalse(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee with email " + email + " not found"));

        return mapToCreateDTO(employeeEntity);

    }

    public List<CreateEmployeeResponseDTO> getEmployeeByDepartment(String department){

        List<EmployeeEntity> employeeEntityList =
                employeeRepository.findByDepartmentAndDeletedIsFalse(department);

        return employeeEntityList.stream()
                .map(this::mapToCreateDTO)
                .toList();

    }

    public List<CreateEmployeeResponseDTO> getEmployeeByName(String name){

        List<EmployeeEntity> employeeEntityList =
                employeeRepository.findByNameContainingIgnoreCaseAndDeletedIsFalse(name);

        return employeeEntityList.stream()
                .map(this::mapToCreateDTO)
                .toList();

    }

    private EmployeeEntity mapToCreateEntity(CreateEmployeeRequestDTO createEmployeeRequestDTO){
        EmployeeEntity employeeEntity=new EmployeeEntity();
        employeeEntity.setSalary(createEmployeeRequestDTO.getSalary());
        employeeEntity.setName(createEmployeeRequestDTO.getName());
        employeeEntity.setDepartment(createEmployeeRequestDTO.getDepartment());
        employeeEntity.setEmail(createEmployeeRequestDTO.getEmail());
        employeeEntity.setDeleted(false);
        employeeEntity.setUpdatedAt(LocalDateTime.now());
        employeeEntity.setCreatedAt(LocalDateTime.now());
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
        createEmployeeResponseDTO.setUpdatedAt(employeeEntity.getUpdatedAt());
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

    private boolean emailExists(EmployeeEntity employeeEntity){
        return employeeRepository.existsByEmail(employeeEntity.getEmail());

    }


}
