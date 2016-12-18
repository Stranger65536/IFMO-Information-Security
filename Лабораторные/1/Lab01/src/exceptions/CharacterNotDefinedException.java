package exceptions;

/**
 * @author vladislav.trofimov@emc.com
 */
public class CharacterNotDefinedException extends CipherHappensException {

    public CharacterNotDefinedException() {
        super();
    }

    public CharacterNotDefinedException(String message) {
        super(message);
    }

    public CharacterNotDefinedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CharacterNotDefinedException(Throwable cause) {
        super(cause);
    }
}