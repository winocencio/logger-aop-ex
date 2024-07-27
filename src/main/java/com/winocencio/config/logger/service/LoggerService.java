package com.winocencio.config.logger.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winocencio.config.logger.ContextApplicationHelper;
import com.winocencio.config.logger.LoggerAop;
import com.winocencio.config.logger.model.LoggerModel;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public abstract class LoggerService {
    private static final Logger logger = LoggerFactory.getLogger(LoggerAop.class);
    private final ObjectMapper objectMapper;
    private final ContextApplicationHelper contextApplicationHelper;

    public void processLogs(Object response, ProceedingJoinPoint proceedingJoinPoint, Instant start) {
        try {

        } catch (Exception ex) {
            logger.error("Error processing logs: " + ex.getMessage());
        }
    }

    abstract LoggerModel getLoggerModelToHistory(Object response, ProceedingJoinPoint proceedingJoinPoint, Instant start) throws JsonProcessingException;
}
