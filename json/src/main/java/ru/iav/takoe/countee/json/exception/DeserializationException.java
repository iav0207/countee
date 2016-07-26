package ru.iav.takoe.countee.json.exception;

/**
 * Created by takoe on 24.07.16.
 */
public class DeserializationException extends RuntimeException {

    public DeserializationException(String message, Throwable cause) {
        super(message, cause);
    }

}
