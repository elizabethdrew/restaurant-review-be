package dev.drew.restaurantreview.exception;

public class InsufficientPermissionException extends RuntimeException {
    public InsufficientPermissionException() {
        super();
    }

    public InsufficientPermissionException(String message) {
        super(message);
    }

    public InsufficientPermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientPermissionException(Throwable cause) {
        super(cause);
    }
}
