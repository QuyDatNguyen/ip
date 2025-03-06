package danchat.exception;

/**
 * Signals an error caused by missing of the date
 */
public class MissingDateException extends DanException{
    public MissingDateException(String message) {
        super(message);
    }
}
