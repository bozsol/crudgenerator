package {PACKAGE}.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GeneralLogger extends SecurityManager {

    private static final Map<String, Logger> loggerMap = new ConcurrentHashMap<>();

    public static Logger getLog() {
        String className = new GeneralLogger().getClassName();
        loggerMap.computeIfAbsent(className, x -> LoggerFactory.getLogger(className));
        return loggerMap.get(className);
    }

    public String getClassName() {
        Class<?>[] classes = getClassContext();
        int i = 0;
        while (classes[i].isAssignableFrom(GeneralLogger.class)) {
            i++;
        }
        return classes[i].getName();
    }

    public static void logRequest() {
        logRequest(null);
    }

    public static void logRequest(Object body) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

            String logMessage = String.format("%s %s", request.getMethod(), request.getRequestURI()) +
                    (request.getQueryString() != null ? String.format("?%s", request.getQueryString()) : "") +
                    (body != null ? String.format("; Body: %s", body) : "");

            getLog().info(logMessage);
        }
    }

    public static void logResponse(HttpStatus status, Object body) {
        String responseText = String.format("HTTP Response: %s %s", status.value(), status.getReasonPhrase()) +
            (body != null ? String.format("; Body: %s", body) : "");
        getLog().info(responseText);
    }
}
