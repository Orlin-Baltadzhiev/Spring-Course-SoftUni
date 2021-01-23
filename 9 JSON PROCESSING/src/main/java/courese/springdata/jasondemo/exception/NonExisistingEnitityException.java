package courese.springdata.jasondemo.exception;

public class NonExisistingEnitityException extends RuntimeException {
    public NonExisistingEnitityException() {
    }

    public NonExisistingEnitityException(String message) {
        super(message);
    }

    public NonExisistingEnitityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonExisistingEnitityException(Throwable cause) {
        super(cause);
    }

    public NonExisistingEnitityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
