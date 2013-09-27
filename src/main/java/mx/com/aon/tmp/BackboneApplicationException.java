package mx.com.aon.tmp;

public class BackboneApplicationException extends Exception {
	private static final long serialVersionUID = -2801559266679891119L;

	public BackboneApplicationException(String message) {
		super(message);
	}

	public BackboneApplicationException(String message, Throwable cause) {
		super(message, cause);
	}
}