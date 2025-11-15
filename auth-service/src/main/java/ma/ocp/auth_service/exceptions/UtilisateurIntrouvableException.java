package ma.ocp.auth_service.exceptions;

public class UtilisateurIntrouvableException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public UtilisateurIntrouvableException(String message) {
        super(message);
    }
}
