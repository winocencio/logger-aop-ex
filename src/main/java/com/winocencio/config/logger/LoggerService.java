package com.winocencio.config.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winocencio.config.logger.model.LoggerModel;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class LoggerService {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAop.class);
    //private final ObjectMapper mapperWithEncrypt;
    private final ObjectMapper objectMapper;
    private final ContextApplicationHelper contextApplicationHelper;

    void processRootApiLogs(Object response, ProceedingJoinPoint proceedingJoinPoint, Instant start) {
        try {
            LoggerModel loggerRootModel = new LoggerModel();

            Object logObject = null;//updateControllerLog(response, proceedingJoinPoint, start, loggerRootModel);;

            if (logObject != null) {
                var json = objectMapper.writeValueAsString(logObject);
                logger.info(json);
            }
        } catch (Exception ex) {
            logger.error("Error processing logs: " + ex.getMessage());
        }
    }

    /*public LoggerRootApiModel updateControllerLog(Object response, ProceedingJoinPoint proceedingJoinPoint, Instant start, LoggerRootApiModel loggerRootModel) throws JsonProcessingException {
        loggerModel.update(applicationName,
                getStatusCodeFromRequest(),
                getHeadersFromRequest(),
                Utils.getLogLevel(response),
                start.toString(),
                Utils.calculateElapsed(start),
                Utils.getClassName(proceedingJoinPoint),
                Utils.getMethodName(proceedingJoinPoint),
                getCorrelationIdFromRequest(),
                Utils.sanitizeRequest(proceedingJoinPoint, mapperWithEncrypt),
                Utils.sanitizeResponse(response, mapperWithEncrypt),
                Utils.getStacktrace(response));

        return loggerRootModel;

    }

    void processLogs(Object response, ProceedingJoinPoint proceedingJoinPoint, Instant start) {
        try {
            //LoggerModel loggerModel = this.getLoggerApplicationFromContext();

            Object logObject;

            if (Utils.isController(proceedingJoinPoint)) {
                logObject = processControllerLog(response, proceedingJoinPoint, start, loggerModel);
            } else {
                logObject = createHistoryLog(response, proceedingJoinPoint, start, loggerModel);
            }

            if (logObject != null) {
                var json = objectMapper.writeValueAsString(logObject);
                logger.info(json);
            }
        } catch (Exception ex) {
            logger.error("Error processing logs: " + ex.getMessage());
        }
    }*/

    /*private Object processControllerLog(Object response, ProceedingJoinPoint proceedingJoinPoint, Instant start, LoggerModel loggerModel) throws JsonProcessingException {

        updateControllerLog(response, proceedingJoinPoint, start, loggerModel);
        return loggerModel;
    }

    private Object createHistoryLog(Object response, ProceedingJoinPoint proceedingJoinPoint, Instant start, LoggerModel loggerModel) throws JsonProcessingException {
        HistoryModel history = createHistory(response, proceedingJoinPoint, start);
        loggerModel.getHistoryModel().add(history);
        return history;
    }

    public void updateControllerLog(Object response, ProceedingJoinPoint proceedingJoinPoint, Instant start, LoggerModel loggerModel) throws JsonProcessingException {
        loggerModel.update(applicationName,
                getStatusCodeFromRequest(),
                getHeadersFromRequest(),
                Utils.getLogLevel(response),
                start.toString(),
                Utils.calculateElapsed(start),
                Utils.getClassName(proceedingJoinPoint),
                Utils.getMethodName(proceedingJoinPoint),
                getCorrelationIdFromRequest(),
                Utils.sanitizeRequest(proceedingJoinPoint, mapperWithEncrypt),
                Utils.sanitizeResponse(response, mapperWithEncrypt),
                Utils.getStacktrace(response));

    }

    private HistoryModel createHistory(Object response, ProceedingJoinPoint proceedingJoinPoint, Instant start) throws JsonProcessingException {
        return HistoryModel.builder()
                .application(applicationName)
                .logLevel(Utils.getLogLevel(response))
                .startTime(start.toString())
                .elapsed(Utils.calculateElapsed(start))
                .className(Utils.getClassName(proceedingJoinPoint))
                .method(Utils.getMethodName(proceedingJoinPoint))
                .correlationId(getCorrelationIdFromRequest())
                .request(Utils.sanitizeRequest(proceedingJoinPoint, mapperWithEncrypt))
                .response(Utils.sanitizeResponse(response, mapperWithEncrypt))
                .stacktrace(Utils.getStacktrace(response))
                .build();
    }

    private Map<String, String> getHeadersFromRequest() {
        return contextApplicationHelper.getHeaders();
    }*/
}
