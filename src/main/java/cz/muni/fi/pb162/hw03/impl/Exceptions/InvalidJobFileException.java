package cz.muni.fi.pb162.hw03.impl.Exceptions;

/**
 * @author Stefan Ockay
 */
public class InvalidJobFileException extends Exception {
    private String message;
    private Throwable cause = null;

    public InvalidJobFileException(String message) {
        this.message = message;
    }

    public InvalidJobFileException(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
