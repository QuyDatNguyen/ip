package danchat.exception;

/**
 * Signals an error when user entered invalid task index
 */
public class InvalidIndexException extends DanException{
    public InvalidIndexException(String message) {
        super(message);
    }
}
