package com.winocencio.config.logger.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winocencio.config.logger.ContextApplicationHelper;
import com.winocencio.config.logger.LoggerAop;
import com.winocencio.config.logger.Utils;
import com.winocencio.config.logger.model.LoggerModel;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class LoggerRootService {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAop.class);
    //private final ObjectMapper mapperWithEncrypt;
    private final ObjectMapper objectMapper;
    private final ContextApplicationHelper contextApplicationHelper;

    @Value("${spring.application.name}")
    private String applicationName;

    public void processRootApiLogs(Object response, ProceedingJoinPoint proceedingJoinPoint, Instant start) {
        try {
            LoggerModel loggerRootModel = this.getLoggerApplicationFromContext();

            Object logObject = loggerRootModel;// updateControllerLog(response, proceedingJoinPoint, start, loggerRootModel);;

            if (logObject != null) {
                var json = objectMapper.writeValueAsString(logObject);
                logger.info(json);
            }
        } catch (Exception ex) {
            logger.error("Error processing logs: " + ex.getMessage());
        }
    }

    public void processHistoryLogs(Object response, ProceedingJoinPoint proceedingJoinPoint, Instant start) {
        try {
            LoggerModel loggerRootModel = this.getLoggerApplicationFromContext();

            LoggerModel loggerModel = createHistory(response,proceedingJoinPoint,start);
            loggerRootModel.getHistory().add(loggerModel);

            Object logObject = loggerModel;// updateControllerLog(response, proceedingJoinPoint, start, loggerRootModel);

            if (logObject != null) {
                var json = objectMapper.writeValueAsString(logObject);
                logger.info(json);
            }
        } catch (Exception ex) {
            logger.error("Error processing logs: " + ex.getMessage());
        }
    }

    private LoggerModel getLoggerApplicationFromContext() {
        return contextApplicationHelper.getLoggerModelFromContext();
    }

    private LoggerModel createHistory(Object response, ProceedingJoinPoint proceedingJoinPoint, Instant start) throws JsonProcessingException {
        LoggerModel loggerMethodModel = new LoggerModel()
        .setApplication(applicationName)
        .setStartTime(start.toString())
        .setElapsed(Utils.calculateElapsed(start))
        .setClassName(Utils.getClassName(proceedingJoinPoint))
        .setMethodName(Utils.getMethodName(proceedingJoinPoint))
        .setCorrelationId(getCorrelationIdFromRequest())
        .setParameters(Utils.getRequest(proceedingJoinPoint))
        .setReturned(Utils.getResponse(response))
        .setStacktrace(Utils.getStacktrace(response));

        return loggerMethodModel;
    }

    private String getCorrelationIdFromRequest() {
        return contextApplicationHelper.getCorrelationId();
    }
}
