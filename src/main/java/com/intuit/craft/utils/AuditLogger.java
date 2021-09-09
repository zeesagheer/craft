package com.intuit.craft.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Audit logger.
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuditLogger {

    private final JacksonUtils jacksonUtils;

    /**
     * Log request.
     *
     * @param httpServletRequest the http servlet request
     * @param body               the body
     */
    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> parameters = buildParametersMap(httpServletRequest);

        stringBuilder.append("REQUEST ");
        stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
        stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
        stringBuilder.append("headers=[").append(buildHeadersMap(httpServletRequest)).append("] ");

        if (!parameters.isEmpty()) {
            stringBuilder.append("parameters=[").append(parameters).append("] ");
        }

        if (body != null) {
            stringBuilder.append("body=[").append(jacksonUtils.objectToJson(body)).append("]");
        }

        log.info(stringBuilder.toString());
    }

    /**
     * Log response.
     *
     * @param httpServletRequest  the http servlet request
     * @param httpServletResponse the http servlet response
     * @param body                the body
     */
    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("RESPONSE ");
        stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
        stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
        stringBuilder.append("responseHeaders=[").append(buildHeadersMap(httpServletResponse)).append("] ");
        stringBuilder.append("responseBody=[").append(jacksonUtils.objectToJsonStr(body)).append("]");
        if (null != httpServletRequest.getAttribute("startTime")) {
            long startTime = (long) httpServletRequest.getAttribute("startTime");
            long timeElapsed = System.currentTimeMillis() - startTime;
            stringBuilder.append(" time=[").append(timeElapsed).append("]");
        }

        log.info(stringBuilder.toString());
    }

    private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = httpServletRequest.getParameter(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }

    private Map<String, String> buildHeadersMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    private Map<String, String> buildHeadersMap(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        Collection<String> headerNames = response.getHeaderNames();
        headerNames.forEach(header -> map.put(header, response.getHeader(header)));

        return map;
    }
}