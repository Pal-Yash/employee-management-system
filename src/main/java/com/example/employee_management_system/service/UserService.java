package com.example.employee_management_system.service;

import com.example.employee_management_system.dto.RegisterUserRequestDTO;
import com.example.employee_management_system.dto.RegisterUserResponseDTO;
import com.example.employee_management_system.entity.UserEntity;
import com.example.employee_management_system.exception.DuplicateResourceException;
import com.example.employee_management_system.exception.ResourceNotFoundException;
import com.example.employee_management_system.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.employee_management_system.dto.LoginRequestDTO;
import com.example.employee_management_system.dto.LoginResponseDTO;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }



    public RegisterUserResponseDTO registerUser(RegisterUserRequestDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException(
                    "User with email " + dto.getEmail() + " already exists");
        }

        UserEntity user = mapToEntity(dto);

        userRepository.save(user);

        return mapToResponseDTO(user);
    }

    private UserEntity mapToEntity(RegisterUserRequestDTO dto) {

        UserEntity user = new UserEntity();

        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());

        return user;
    }

    private RegisterUserResponseDTO mapToResponseDTO(UserEntity user) {

        RegisterUserResponseDTO response = new RegisterUserResponseDTO();

        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        return response;
    }

    public LoginResponseDTO loginUser(LoginRequestDTO dto) {

        UserEntity user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User with email " + dto.getEmail() + " not found"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponseDTO(token);
    }
}