package exceptions;

/**
 * @author vladislav.trofimov@emc.com
 */
public class CharacterAlreadyDefinedException extends CipherHappensException {

    public CharacterAlreadyDefinedException() {
        super();
    }

    public CharacterAlreadyDefinedException(String message) {
        super(message);
    }

    public CharacterAlreadyDefinedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CharacterAlreadyDefinedException(Throwable cause) {
        super(cause);
    }
}