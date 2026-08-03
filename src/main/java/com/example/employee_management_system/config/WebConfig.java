package com.example.employee_management_system.config;

import com.example.employee_management_system.interceptor.EmployeeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final EmployeeInterceptor employeeInterceptor;

    public WebConfig(EmployeeInterceptor employeeInterceptor) {
        this.employeeInterceptor = employeeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(employeeInterceptor)
                .addPathPatterns("/employees/**");

    }
}