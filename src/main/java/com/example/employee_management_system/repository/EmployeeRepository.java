package com.example.employee_management_system.repository;

import com.example.employee_management_system.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    List<EmployeeEntity> findByDeletedIsFalse();

    Optional<EmployeeEntity> findByIdAndDeletedIsFalse(Long id);

    Boolean existsByEmail(String emailId);

    Page<EmployeeEntity> findByDeletedIsFalse(Pageable pageable);

    Optional<EmployeeEntity> findByEmailAndDeletedIsFalse(String email);

    Page<EmployeeEntity> findByDepartmentAndDeletedIsFalse(Pageable pageable, String department);

    List<EmployeeEntity> findByNameContainingIgnoreCaseAndDeletedIsFalse(String name);
}
