package ma.ocp.auth_service.dto;

import lombok.Data;

@Data
public class TechnicienDTO extends UtilisateurDTO {
    private Integer niveau;
}