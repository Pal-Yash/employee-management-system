package com.example.employee_management_system.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(2)
public class LoggingFilter implements Filter{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)throws IOException, ServletException {
        Long startTime=System.currentTimeMillis();
        HttpServletRequest httpServletRequest=(HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse=(HttpServletResponse) servletResponse;
        String requestid = UUID.randomUUID().toString();

        httpServletResponse.setHeader("X-Request-ID: ",requestid);

        System.out.println("Incoming Request: "+httpServletRequest.getMethod() +" "+httpServletRequest.getRequestURI());
        try {
            filterChain.doFilter(servletRequest,servletResponse);
        }
        finally {
            Long duration=System.currentTimeMillis()-startTime;
            System.out.println("Rseponse status:"+httpServletResponse.getStatus());
            System.out.println("Rseponse timr:"+duration);
        }


    }
}
