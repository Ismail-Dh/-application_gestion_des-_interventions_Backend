package ma.ocp.auth_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UtilisateurIntrouvableException.class)
    public ResponseEntity<String> handleUtilisateurIntrouvable(UtilisateurIntrouvableException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MotDePasseIncorrectException.class)
    public ResponseEntity<String> handleMotDePasseIncorrect(MotDePasseIncorrectException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}
