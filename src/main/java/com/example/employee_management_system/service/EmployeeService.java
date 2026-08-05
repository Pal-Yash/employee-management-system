package com.example.employee_management_system.service;

import com.example.employee_management_system.dto.CreateEmployeeRequestDTO;
import com.example.employee_management_system.dto.CreateEmployeeResponseDTO;
import com.example.employee_management_system.dto.UpdateEmployeeRequestDTO;
import com.example.employee_management_system.dto.UpdateEmployeeResponseDTO;
import com.example.employee_management_system.entity.EmployeeEntity;
import com.example.employee_management_system.exception.DuplicateResourceException;
import com.example.employee_management_system.exception.ResourceNotFoundException;
import com.example.employee_management_system.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeService {
    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;


    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public CreateEmployeeResponseDTO createEmployee(CreateEmployeeRequestDTO createEmployeeRequestDTO) {
        EmployeeEntity employeeEntity = mapToCreateEntity(createEmployeeRequestDTO);
        if (emailExists(employeeEntity)) {


            throw new DuplicateResourceException(
                    "Employee with " + employeeEntity.getEmail() + " already exists");
        }
        employeeRepository.save(employeeEntity);
        logger.info("Employee created successfully with id {}", employeeEntity.getId());
        return mapToCreateDTO(employeeEntity);
    }

    public CreateEmployeeResponseDTO getEmployee(Long id) {
        EmployeeEntity employeeEntityResp = employeeRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " not found"));
        logger.info("Employee fetched with id {}", id);
        return mapToCreateDTO(employeeEntityResp);
    }

    public UpdateEmployeeResponseDTO updateEmployee(Long id, UpdateEmployeeRequestDTO updateEmployeeRequestDTO) {

        EmployeeEntity employeeEntity = employeeRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " not found"));

        if (!employeeEntity.getEmail().equals(updateEmployeeRequestDTO.getEmail())
                && employeeRepository.existsByEmail(updateEmployeeRequestDTO.getEmail())) {


            throw new DuplicateResourceException(
                    "Employee with email "
                            + updateEmployeeRequestDTO.getEmail()
                            + " already exists");
        }

        employeeEntity.setEmail(updateEmployeeRequestDTO.getEmail());
        employeeEntity.setName(updateEmployeeRequestDTO.getName());

        employeeRepository.save(employeeEntity);
        logger.info("Employee updated successfully with id {}", id);
        return mapToUpdateDTO(employeeEntity);
    }

    public void deleteEmployee(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " not found"));
        ;
        logger.info("Employee deleted permanently with id {}", id);
        employeeRepository.delete(employeeEntity);
    }

    public void softDelete(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " not found"));

        employeeEntity.setDeleted(true);
        logger.info("Employee soft deleted with id {}", id);
        employeeRepository.save(employeeEntity);
    }

    public Page<CreateEmployeeResponseDTO> getAllEmployees(Pageable pageable) {

        logger.info("Fetching employees. Page: {}, Size: {}",
                pageable.getPageNumber(),
                pageable.getPageSize());

        Page<EmployeeEntity> employeePage =
                employeeRepository.findByDeletedIsFalse(pageable);

        return employeePage.map(this::mapToCreateDTO);
    }

    public CreateEmployeeResponseDTO getEmployeeByEmail(String email) {

        EmployeeEntity employeeEntity = employeeRepository
                .findByEmailAndDeletedIsFalse(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee with email " + email + " not found"));

        logger.info("Employee fetched with email {}", email);
        return mapToCreateDTO(employeeEntity);

    }

    public Page<CreateEmployeeResponseDTO> getEmployeeByDepartment(Pageable pageable, String department) {

        Page<EmployeeEntity> employeePage =
                employeeRepository.findByDepartmentAndDeletedIsFalse(pageable, department);
        logger.info("Employees fetched for department {}", department);
        return employeePage.map(this::mapToCreateDTO);

    }

    public List<CreateEmployeeResponseDTO> getEmployeeByName(String name) {

        List<EmployeeEntity> employeeEntityList =
                employeeRepository.findByNameContainingIgnoreCaseAndDeletedIsFalse(name);
        logger.info("Employees searched by name {}", name);
        return employeeEntityList.stream()
                .map(this::mapToCreateDTO)
                .toList();


    }

    private EmployeeEntity mapToCreateEntity(CreateEmployeeRequestDTO createEmployeeRequestDTO) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setSalary(createEmployeeRequestDTO.getSalary());
        employeeEntity.setName(createEmployeeRequestDTO.getName());
        employeeEntity.setDepartment(createEmployeeRequestDTO.getDepartment());
        employeeEntity.setEmail(createEmployeeRequestDTO.getEmail());
        employeeEntity.setDeleted(false);

        return employeeEntity;
    }

    private CreateEmployeeResponseDTO mapToCreateDTO(EmployeeEntity employeeEntity) {
        CreateEmployeeResponseDTO createEmployeeResponseDTO = new CreateEmployeeResponseDTO();
        createEmployeeResponseDTO.setDepartment(employeeEntity.getDepartment());
        createEmployeeResponseDTO.setEmail(employeeEntity.getEmail());

        createEmployeeResponseDTO.setName(employeeEntity.getName());
        createEmployeeResponseDTO.setSalary(employeeEntity.getSalary());
        createEmployeeResponseDTO.setId(employeeEntity.getId());
        createEmployeeResponseDTO.setCreatedAt(employeeEntity.getCreatedAt());
       createEmployeeResponseDTO.setUpdatedAt(employeeEntity.getUpdatedAt());

        return createEmployeeResponseDTO;
    }


    private UpdateEmployeeResponseDTO mapToUpdateDTO(EmployeeEntity employeeEntity) {
        UpdateEmployeeResponseDTO updateEmployeeResponseDTO = new UpdateEmployeeResponseDTO();
        updateEmployeeResponseDTO.setDepartment(employeeEntity.getDepartment());
        updateEmployeeResponseDTO.setEmail(employeeEntity.getEmail());

        updateEmployeeResponseDTO.setName(employeeEntity.getName());
        updateEmployeeResponseDTO.setSalary(employeeEntity.getSalary());
        updateEmployeeResponseDTO.setId(employeeEntity.getId());

        updateEmployeeResponseDTO.setUpdatedAt(employeeEntity.getUpdatedAt());

        return updateEmployeeResponseDTO;
    }

    private boolean emailExists(EmployeeEntity employeeEntity) {

        return employeeRepository.existsByEmail(employeeEntity.getEmail());

    }


}
