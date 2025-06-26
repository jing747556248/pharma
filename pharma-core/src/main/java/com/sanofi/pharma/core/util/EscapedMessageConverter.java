package com.sanofi.pharma.core.util;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EscapedMessageConverter extends MessageConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convert(ILoggingEvent event) {
        Map<String, Object> logMap = new LinkedHashMap<>();
        logMap.put("appName", "pharma");
        logMap.put("traceId", event.getMDCPropertyMap().getOrDefault("traceId", ""));
        logMap.put("spanId", event.getMDCPropertyMap().getOrDefault("spanId", ""));
        logMap.put("thread", event.getThreadName());
        logMap.put("message", getMessageWithStackTrace(event));

        try {
            return objectMapper.writeValueAsString(logMap);
        } catch (IOException e) {
            return "{\"error\": \"Failed to serialize log message to JSON\"}";
        }
    }

    private Map<String, Object> getMessageWithStackTrace(ILoggingEvent event) {
        Map<String, Object> messageMap = new LinkedHashMap<>();
        String message = super.convert(event);
        messageMap.put("text", message.replace("\"", "\\\""));

        IThrowableProxy throwableProxy = event.getThrowableProxy();
        if (throwableProxy != null) {
            messageMap.put("stackTrace", convertThrowable(throwableProxy));
        }

        return messageMap;
    }

    private Map<String, Object> convertThrowable(IThrowableProxy throwableProxy) {
        Map<String, Object> stackTraceMap = new LinkedHashMap<>();
        stackTraceMap.put("className", throwableProxy.getClassName());
        stackTraceMap.put("message", throwableProxy.getMessage());

        List<StackTraceElementProxy> stackTraceElementProxies = List.of(throwableProxy.getStackTraceElementProxyArray());
        stackTraceMap.put("stackTraceElements", stackTraceElementProxies.stream()
                .map(StackTraceElementProxy::getStackTraceElement)
                .map(this::convertStackTraceElement)
                .toArray());

        IThrowableProxy[] suppressed = throwableProxy.getSuppressed();
        if (suppressed != null) {
            stackTraceMap.put("suppressed", convertThrowables(suppressed));
        }

        IThrowableProxy cause = throwableProxy.getCause();
        if (cause != null) {
            stackTraceMap.put("cause", convertThrowable(cause));
        }

        return stackTraceMap;
    }

    private Map<String, String> convertStackTraceElement(StackTraceElement element) {
        Map<String, String> elementMap = new LinkedHashMap<>();
        elementMap.put("className", element.getClassName());
        elementMap.put("fileName", element.getFileName());
        elementMap.put("methodName", element.getMethodName());
        elementMap.put("lineNumber", String.valueOf(element.getLineNumber()));
        return elementMap;
    }

    private List<Map<String, Object>> convertThrowables(IThrowableProxy[] throwables) {
        return List.of(List.of(throwables).stream()
                .map(this::convertThrowable)
                .toArray(Map[]::new));
    }
}