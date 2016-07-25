package ru.iav.takoe.statbm.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogService {

    private static final Logger logger = LoggerFactory.getLogger("StatBM Logger");

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logWarning(String message) {
        logger.warn(message);
    }

    public static void logWarning(String message, Throwable cause) {
        logger.warn(message, cause);
    }

    public static void logError(String message) {
        logger.error(message);
    }

    public static void logError(String message, Throwable cause) {
        logger.error(message, cause);
    }

}
