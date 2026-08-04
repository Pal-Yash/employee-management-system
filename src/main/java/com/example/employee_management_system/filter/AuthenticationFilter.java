package com.example.employee_management_system.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class AuthenticationFilter implements Filter {

    private static final Logger logger =
            LoggerFactory.getLogger(AuthenticationFilter.class);


    private static final String TOKEN_HEADER = "token";
    private static final String API_KEY_HEADER = "key";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String token = httpServletRequest.getHeader(TOKEN_HEADER);
        String apiKey = httpServletRequest.getHeader(API_KEY_HEADER);


        String path = httpServletRequest.getRequestURI();

        if (path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        if (token == null || !token.equals("12345")) {
            logger.warn("Authentication failed. Invalid token for {}",
                    httpServletRequest.getRequestURI());
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        if (apiKey == null || !apiKey.equals("123")) {

            logger.warn("Authentication failed. Invalid API key for {}",
                    httpServletRequest.getRequestURI());
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        logger.info("Authentication successful for {}", httpServletRequest.getRequestURI());
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
