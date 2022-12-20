package com.musala.drones.commons.aop.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;


@Aspect
@Configuration
@Slf4j
public class MethodCallAndReturn {

    private static final String POINT_CUT = "within(com.musala.drones.commons.aop.logging.Loggable+)";

    private final ObjectMapper objectMapper;

    public MethodCallAndReturn(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private String convertObjectToString(Object obj) {
        try {
            return this.objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        return "";
    }

    @Before(POINT_CUT)
    public void printMethodArguments(JoinPoint joinPoint) {
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        final String[] parameterNames = signature.getParameterNames();
        final Object[] parameterValues = joinPoint.getArgs();
        final Map<String, Object> methodArguments = IntStream.range(0, parameterNames.length).boxed().collect(HashMap::new, (map, i) -> map.put(parameterNames[i], parameterValues[i]), HashMap::putAll);

        final String methodArgumentsString = this.convertObjectToString(methodArguments);
        log.debug("METHOD_CALL, {}, {}", signature, methodArgumentsString);
    }


    @AfterReturning(value = POINT_CUT, returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        final String returnObjectString = this.convertObjectToString(result);
        log.debug("METHOD_RETURN, {}, {}", signature, returnObjectString);
    }


    @AfterThrowing(value = POINT_CUT, throwing = "ex")
    public void handleThrownException(JoinPoint joinPoint, Throwable ex) {
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        final String exceptionName = ex.getClass().getName();
        final String exceptionMessage = this.convertObjectToString(ex.getMessage());
        log.error("METHOD_EXCEPTION, {}, {}, {}", signature, exceptionName, exceptionMessage);
    }
}
