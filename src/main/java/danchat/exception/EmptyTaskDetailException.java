package danchat.exception;

/**
 * Signals an error caused by empty task detail from user input
 */
public class EmptyTaskDetailException extends DanException{
    public EmptyTaskDetailException(String message) {
        super(message);
    }
}
