package danchat.exception;

/**
 * Signals an error due to entering date in incorrect format
 */
public class InvalidDateException extends DanException {
    public InvalidDateException(String message) {
        super(message);
    }
}
