package ma.ocp.Demande_Intervention.dto;

import java.util.Date;
import lombok.Data;
import ma.ocp.Demande_Intervention.entities.DemandeIntervention;

@Data
public class DemandeInterventionDTO {
    private Long id;
    private String titre;
    private String description;
    private Date dateDemande;
    private String statut; // Ex : EN_ATTENTE, VALIDÉE, REJETÉE, TERMINÉE
    private Long equipementId;
    private Long utilisateurId;
    private String lieu;
    private String niveauIntervention;
    private String priorite;
	
	
}