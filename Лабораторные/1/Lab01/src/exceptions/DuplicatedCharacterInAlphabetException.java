package exceptions;

/**
 * @author vladislav.trofimov@emc.com
 */
public class DuplicatedCharacterInAlphabetException extends CipherHappensException {

    public DuplicatedCharacterInAlphabetException() {
        super();
    }

    public DuplicatedCharacterInAlphabetException(String message) {
        super(message);
    }

    public DuplicatedCharacterInAlphabetException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedCharacterInAlphabetException(Throwable cause) {
        super(cause);
    }
}
