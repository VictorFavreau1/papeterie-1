package fr.eni.papeterie.dal;

/**
 * Exception personnalisée
 */
public class DALException extends Exception {
    public DALException(String message) {
        super(message);
    }

    public DALException(String message, Throwable cause) {
        super(message, cause);
    }
}
