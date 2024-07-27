package com.winocencio.config.logger;

import com.winocencio.config.logger.model.LoggerModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ContextApplicationHelper {

    @Value("${spring.application.name}")
    private String applicationName;

    private final HttpServletRequest request;
    private final HttpServletResponse response;


    public LoggerModel getLoggerModelFromContext() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(this::tryGetLoggerModel)
                .orElseThrow(() -> new IllegalStateException("RequestContextHolder.getRequestAttributes() return null"));
    }

    private LoggerModel tryGetLoggerModel(RequestAttributes requestAttributes) {
        return Optional.ofNullable((LoggerModel) requestAttributes.getAttribute("logger", RequestAttributes.SCOPE_REQUEST))
                .orElseGet(() -> {
                    LoggerModel logger = new LoggerModel();
                    logger.setApplication(applicationName);
                    requestAttributes.setAttribute("logger", logger, RequestAttributes.SCOPE_REQUEST);
                    return logger;
                });
    }

    public String getCorrelationId() {
        request.getServletPath();
        request.getMethod();
        return (String) RequestContextHolder.getRequestAttributes().getAttribute("correlation-id", RequestAttributes.SCOPE_REQUEST);
    }
}
