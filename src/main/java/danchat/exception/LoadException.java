package danchat.exception;

/**
 * Signal an error during loading process of storage file
 */
public class LoadException extends DanException {
    public LoadException(String message) {
        super(message);
    }
}
