package com.example.employee_management_system.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class EmployeeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        System.out.println("Controller : "
                + handlerMethod.getBeanType().getSimpleName());

        System.out.println("Method : "
                + handlerMethod.getMethod().getName());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           org.springframework.web.servlet.ModelAndView modelAndView)
            throws Exception {

        System.out.println("Interceptor : After Controller");
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex)
            throws Exception {

        System.out.println("Interceptor : Request Completed");
    }
}