package com.example.employee_management_system.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import jakarta.servlet.Filter;

@Component
@Order(1)
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)throws IOException, ServletException{
        HttpServletResponse httpServletResponse=(HttpServletResponse) servletResponse;
        HttpServletRequest httpServletRequest=(HttpServletRequest) servletRequest;
        String token=httpServletRequest.getHeader("token");
        String apiKey= httpServletRequest.getHeader("key");

        String path = httpServletRequest.getRequestURI();

        if (path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        if(token==null || !token.equals("12345")){
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        if(apiKey==null || !apiKey.equals("123")){
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpServletResponse.setContentType("application/json");
            httpServletResponse.getWriter().write("{\n" +
                    "    \"message\" : \"Invalid or missing Api Key\"\n" +
                    "}");
            return;
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
