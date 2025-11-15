package ma.ocp.auth_service.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TechnicienDTO.class, name = "TECHNICIEN"),
        @JsonSubTypes.Type(value = ResponsableDTO.class, name = "RESPONSABLE"),
        @JsonSubTypes.Type(value = DemandeurDTO.class, name = "DEMANDEUR"),
        @JsonSubTypes.Type(value = UtilisateurDTO.class, name = "ADMINISTRATEUR"),
        
})

@Data
public class UtilisateurDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String matricule;
    private String motDePasse;
    private String telephone;
    private String email;
    private String Statu;
    private Date dateDeCreation = new Date();
    private String type; // ADMINISTRATEUR, RESPONSABLE, DEMANDEUR, TECHNICIEN
}