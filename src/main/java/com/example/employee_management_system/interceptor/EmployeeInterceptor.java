package com.example.employee_management_system.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class EmployeeInterceptor implements HandlerInterceptor {

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        logger.info(
                "Controller: {}, Method: {}",
                handlerMethod.getBeanType().getSimpleName(),
                handlerMethod.getMethod().getName()
        );

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           org.springframework.web.servlet.ModelAndView modelAndView)
            throws Exception {

        logger.info(
                "Controller execution completed for {}",
                request.getRequestURI()
        );
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex)
            throws Exception {

        logger.info(
                "Request completed for {} with status {}",
                request.getRequestURI(),
                response.getStatus()
        );
    }
}