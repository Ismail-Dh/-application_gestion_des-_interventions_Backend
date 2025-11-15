package ma.ocp.auth_service.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String matricule;
    private String motDePasse;
}