package com.winocencio.config.logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Parameter;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

public class Utils {

    public static String getLogLevel(Object response) {
        return isException(response) ? "ERROR" : "INFO";
    }

    public static Boolean isController(ProceedingJoinPoint joinPoint) {
        Class<?> targetClass = joinPoint.getTarget().getClass();
        return targetClass.isAnnotationPresent(RestController.class);
    }

    public static Object sanitizeResponse(Object response, ObjectMapper mapperWithEncypt) throws JsonProcessingException {
        if (isException(response)) {
            return ((Throwable) response).getMessage();
        }
        return fieldToJson(response, mapperWithEncypt);
    }

    public static Object getResponse(Object response) throws JsonProcessingException {
        if (isException(response)) {
            return fieldToJson(((Throwable) response).getMessage(), new ObjectMapper());
        }
        return fieldToJson(response,new ObjectMapper());
    }

    private static Boolean isException(Object response) {
        return response instanceof Throwable;
    }

    public static Object sanitizeRequest(ProceedingJoinPoint proceedingJoinPoint, ObjectMapper mapper) {

        Object[] fields = getFieldsFromParameter(proceedingJoinPoint);
        Parameter[] parameters = getParameters(proceedingJoinPoint);

        return IntStream.range(0, parameters.length)
                .mapToObj(index -> {
                    try {
                        if (hasParameterWithEncrypt(parameters[index])) {
                            return encrypt();
                        } else {
                            return fieldToJson(fields[index], mapper);
                        }
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

    public static Object getRequest(ProceedingJoinPoint proceedingJoinPoint) {

        Object[] fields = getFieldsFromParameter(proceedingJoinPoint);
        Parameter[] parameters = getParameters(proceedingJoinPoint);

        return fieldsToJson(fields);
    }

    private static List<String> fieldsToJson(Object[] fields) {
        return List.of(fields).stream().map(o -> {
            try {
                return fieldToJson(o, new ObjectMapper());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());
    }

    private static String fieldToJson(Object object, ObjectMapper mapper) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    private static Parameter[] getParameters(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        return signature.getMethod().getParameters();
    }

    private static Object[] getFieldsFromParameter(ProceedingJoinPoint proceedingJoinPoint) {
        return proceedingJoinPoint.getArgs();
    }

    private static boolean hasEncryptAnnotation(Parameter arg) {
        return false;
    }

    private static Boolean hasParameterWithEncrypt(Parameter parameter) {
        return !isNull(parameter) && hasEncryptAnnotation(parameter);
    }

    public static String calculateElapsed(Instant start) {
        return Duration.between(start, Instant.now()).toMillis() + "";
    }

    public static String getClassName(ProceedingJoinPoint proceedingJoinPoint) {
        return proceedingJoinPoint.getTarget().getClass().getSimpleName();
    }

    public static String getMethodName(ProceedingJoinPoint proceedingJoinPoint) {
        return proceedingJoinPoint.getSignature().getName();
    }

    public static String getStacktrace(Object response) {
        return response instanceof Throwable ? Arrays.toString(((Throwable) response).getStackTrace()) : null;
    }

    public static String encrypt() {
        return "\"*****\"";
    }
}
