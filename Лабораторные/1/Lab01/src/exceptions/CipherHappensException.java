package exceptions;

/**
 * @author vladislav.trofimov@emc.com
 */
public class CipherHappensException extends Exception {

    protected CipherHappensException() {
        super();
    }

    protected CipherHappensException(String message) {
        super(message);
    }

    protected CipherHappensException(String message, Throwable cause) {
        super(message, cause);
    }

    protected CipherHappensException(Throwable cause) {
        super(cause);
    }
}
