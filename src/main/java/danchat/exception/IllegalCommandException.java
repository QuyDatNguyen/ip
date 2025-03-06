package danchat.exception;

/**
 * Signals an error caused by unrecognized command or when command in incorrect format
 */
public class IllegalCommandException extends DanException{
    public IllegalCommandException(String message) {
        super(message);
    }
}
