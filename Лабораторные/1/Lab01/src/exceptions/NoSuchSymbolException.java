package exceptions;

/**
 * @author vladislav.trofimov@emc.com
 */
public class NoSuchSymbolException extends CipherHappensException {

    public NoSuchSymbolException() {
        super();
    }

    public NoSuchSymbolException(String message) {
        super(message);
    }

    public NoSuchSymbolException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchSymbolException(Throwable cause) {
        super(cause);
    }
}
