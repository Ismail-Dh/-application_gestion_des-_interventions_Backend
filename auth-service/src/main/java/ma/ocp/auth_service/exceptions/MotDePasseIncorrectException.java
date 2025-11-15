package ma.ocp.auth_service.exceptions;

public class MotDePasseIncorrectException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	 public MotDePasseIncorrectException(String message) {
	        super(message);
	    }
}
