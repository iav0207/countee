package ru.iav.takoe.countee.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogService {

    private static final Logger logger = LoggerFactory.getLogger("Countee Logger");

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logWarning(String message) {
        logger.warn(message);
    }

    public static void logWarning(String message, Throwable cause) {
        logger.warn(message, cause);
    }

    public static void logDebug(Throwable t) {
        logDebug(t.getMessage(), t);
    }

    public static void logDebug(String message) {
        logger.debug(message);
    }

    public static void logDebug(String message, Throwable throwable) {
        logger.debug(message, throwable);
    }

    public static void logError(String message) {
        logger.error(message);
    }

    public static void logError(String message, Throwable cause) {
        logger.error(message, cause);
    }

}
