package se.lexicon.g1.marketplaceapiproject.exception;

public class DataDuplicateException extends RuntimeException {
    public DataDuplicateException(String message) {
        super(message);
    }

    public DataDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}
