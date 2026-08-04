package com.example.employee_management_system.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(2)
public class LoggingFilter implements Filter {

    private static final Logger logger =
            LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String requestid = UUID.randomUUID().toString();

        httpServletResponse.setHeader("X-Request-ID", requestid);

        logger.info(
                "Incoming request: {} {}",
                httpServletRequest.getMethod(),
                httpServletRequest.getRequestURI()
        );
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            logger.info(
                    "Response status: {}",
                    httpServletResponse.getStatus()
            );
            logger.info(
                    "Response time: {} ms",
                    duration
            );
        }


    }
}
