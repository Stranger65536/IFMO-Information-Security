package exceptions;

/**
 * @author vladislav.trofimov@emc.com
 */
public class EmptyAlphabetException extends CipherHappensException {

    public EmptyAlphabetException() {
        super();
    }

    public EmptyAlphabetException(String message) {
        super(message);
    }

    public EmptyAlphabetException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyAlphabetException(Throwable cause) {
        super(cause);
    }
}
