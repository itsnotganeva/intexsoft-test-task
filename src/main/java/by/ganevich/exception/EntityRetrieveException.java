package by.ganevich.exception;

public class EntityRetrieveException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public EntityRetrieveException() {
        super();
    }

    public EntityRetrieveException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public EntityRetrieveException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityRetrieveException(String message) {
        super(message);
    }

    public EntityRetrieveException(Throwable cause) {
        super(cause);
    }

}
