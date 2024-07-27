package com.winocencio.config.logger.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Accessors(chain = true)
//@JsonPropertyOrder({"application", "correlationId", "status", "startTime", "elapsed", "logLevel", "className", "methodName", "request", "response", "stacktrace", "history"})
public class LoggerModel {
    private String application;
    private String server;
    private String correlationId; //get differents modes, each impl has one;
    private String startTime;
    private String elapsed;
    private String stacktrace;
    private String logLevel;
    private String className;
    private String methodName;
    @JsonRawValue
    private Object parameters;
    @JsonRawValue
    private Object returned;

    private String message;

    private List<LoggerModel> history = new ArrayList<>();

    private String requestMethod;
    private String requestPath;
    @JsonRawValue
    private String requestBody;

    @JsonRawValue
    private String response;

    private String statusCode;

    @JsonRawValue
    private String headers;

    private String originType; // TODO: Create Enum
    @JsonRawValue
    private String origin;
}



