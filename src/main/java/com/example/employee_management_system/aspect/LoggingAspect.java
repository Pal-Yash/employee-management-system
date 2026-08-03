package com.example.employee_management_system.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.example.employee_management_system.service.*.*(..))")
    public void serviceMethods(){}

    @Around("serviceMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();

        System.out.println(
                joinPoint.getSignature().getName()
                        + " executed in "
                        + (endTime - startTime)
                        + " ms");

        return result;
    }

    @AfterReturning("serviceMethods()")
    public void logSuccess(JoinPoint joinPoint) {

        System.out.println(
                "Method executed successfully : "
                        + joinPoint.getSignature().getName());

    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {

        System.out.println(
                "Exception in method : "
                        + joinPoint.getSignature().getName());

        System.out.println(
                "Exception Message : "
                        + exception.getMessage());

    }

}