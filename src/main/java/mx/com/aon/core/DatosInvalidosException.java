package mx.com.aon.core;

public class DatosInvalidosException extends ApplicationException {

	private static final long serialVersionUID = 7290723179683594952L;

    /**
     * @param message
     */
    public DatosInvalidosException(String message) {
        super(message);

    }

    /**
     * @param message
     * @param cause
     */
    public DatosInvalidosException(String message, Throwable cause) {
        super(message, cause);

    }

}