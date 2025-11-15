package ma.ocp.auth_service.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String token;        // JWT ou session ID
    private String type;         // Type utilisateur (ex: ADMINISTRATEUR)
    private String nom;
    private String prenom;
    private String matricule;
    private Long id;
}

