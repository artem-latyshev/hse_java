package hse.vpi19.banking.exception;

public class ApiException extends Exception {
    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
