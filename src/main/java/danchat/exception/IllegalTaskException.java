package danchat.exception;

/**
 * Signals an error caused by attempt to modify a task that does not exist
 */
public class IllegalTaskException extends DanException{
    public IllegalTaskException(String message) {
        super(message);
    }
}
