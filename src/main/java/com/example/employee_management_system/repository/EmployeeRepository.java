package com.example.employee_management_system.repository;
import com.example.employee_management_system.dto.CreateEmployeeRequestDTO;
import com.example.employee_management_system.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long> {
    List<EmployeeEntity> findByDeletedIsFalse();
    Optional<EmployeeEntity> findByIdAndDeletedIsFalse(Long id);
}
