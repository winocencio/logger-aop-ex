package com.winocencio.config.logger;

import com.winocencio.config.logger.service.LoggerRootService;
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
public class LoggerAopRoot {

    private final LoggerRootService loggerService;

    @Pointcut("@within(com.winocencio.config.logger.annotation.LoggerRootApiInvocation)")
    public void loggerRootApiInvocation() {
    }

    @Around("loggerRootApiInvocation()")
    public Object aroundMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        Instant start = Instant.now();
        try {
            Object result = joinPoint.proceed();

            loggerService.processRootApiLogs(result, joinPoint, start);
            return result;
        } catch (Exception ex) {
            loggerService.processRootApiLogs(ex, joinPoint, start);
            throw ex;
        }
    }
}
