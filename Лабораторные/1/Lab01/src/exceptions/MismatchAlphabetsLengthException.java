package exceptions;

/**
 * @author vladislav.trofimov@emc.com
 */
public class MismatchAlphabetsLengthException extends CipherHappensException {

    public MismatchAlphabetsLengthException() {
        super();
    }

    public MismatchAlphabetsLengthException(String message) {
        super(message);
    }

    public MismatchAlphabetsLengthException(String message, Throwable cause) {
        super(message, cause);
    }

    public MismatchAlphabetsLengthException(Throwable cause) {
        super(cause);
    }
}
