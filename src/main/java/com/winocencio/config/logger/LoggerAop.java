package com.winocencio.config.logger;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggerAop {

    private final LoggerService loggerService;

    @Pointcut("@annotation(com.winocencio.config.logger.annotation.LoggerMethodInvocation)")
    public void loggerMethodInvocationPointcut() {
    }

    @Pointcut("@within(com.winocencio.config.logger.annotation.LoggerRootApiInvocation)")
    public void restController() {
    }

    @Around("loggerMethodInvocationPointcut() || restController()")
    public Object aroundMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        Instant start = Instant.now();
        try {
            Object result = joinPoint.proceed();

           // loggerService.processLogs(result, joinPoint, start);
            return result;
        } catch (Exception ex) {
           // loggerService.processLogs(ex, joinPoint, start);
            throw ex;
        }
    }
}
