package com.example.employee_management_system.dto;

public class LoginResponseDTO {

    private String token;

    public LoginResponseDTO() {
    }

    public String getToken() {
        return token;
    }

    public LoginResponseDTO(String token) {
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}