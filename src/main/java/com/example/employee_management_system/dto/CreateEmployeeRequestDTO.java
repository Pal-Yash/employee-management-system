package com.example.employee_management_system.dto;

import jakarta.validation.constraints.*;

public class CreateEmployeeRequestDTO {
    @NotBlank(message = "Name cannot be blank")
    @Size(min=2, max=50, message ="Name should be of 2 to 50 characters")
    private String name;
    @NotBlank(message = "Department cannot be blank")
    @Size(min=2, max=50, message ="Department name should be of 2 to 50 characters")
    private String department;
    @Positive(message = "Salary cannot be negative")
    @NotNull(message = "Salary cannot be blank")
    private Double salary;

    @Email(message = "Enter valid email")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setName(String name) {
        this.name = name;
    }
}
