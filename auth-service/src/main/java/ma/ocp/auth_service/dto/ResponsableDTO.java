package ma.ocp.auth_service.dto;

import lombok.Data;

@Data
public class ResponsableDTO extends UtilisateurDTO {
	private String typeService;
}
