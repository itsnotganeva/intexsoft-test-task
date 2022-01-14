package by.ganevich.exception;

public class EntityDeleteException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public EntityDeleteException() {
        super();
    }

    public EntityDeleteException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public EntityDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityDeleteException(String message) {
        super(message);
    }

    public EntityDeleteException(Throwable cause) {
        super(cause);
    }

}
