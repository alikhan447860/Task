package exceptions;

/**
 * Custom exception for framework-level errors.
 * Wraps underlying exceptions to provide meaningful error messages.
 */
public class FrameworkException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a FrameworkException with the specified detail message.
     *
     * @param message the detail message
     */
    public FrameworkException(String message) {
        super(message);
    }

    /**
     * Constructs a FrameworkException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a FrameworkException with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public FrameworkException(Throwable cause) {
        super(cause);
    }
}
