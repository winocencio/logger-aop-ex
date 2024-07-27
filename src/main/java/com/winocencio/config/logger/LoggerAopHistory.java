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
public class LoggerAopHistory {

    private final LoggerRootService loggerService;

    @Pointcut("@annotation(com.winocencio.config.logger.annotation.LoggerMethodInvocation)")
    public void loggerMethodInvocationPointcut() {}

    @Around("loggerMethodInvocationPointcut()")
    public Object aroundMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        Instant start = Instant.now();
        try {
            Object result = joinPoint.proceed();

            loggerService.processHistoryLogs(result, joinPoint, start);
            return result;
        } catch (Exception ex) {
            loggerService.processHistoryLogs(ex, joinPoint, start);
            throw ex;
        }
    }
}
