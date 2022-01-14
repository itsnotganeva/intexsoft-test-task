package by.ganevich.exception;

public class EntitySaveException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public EntitySaveException() {
        super();
    }

    public EntitySaveException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public EntitySaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntitySaveException(String message) {
        super(message);
    }

    public EntitySaveException(Throwable cause) {
        super(cause);
    }

}
