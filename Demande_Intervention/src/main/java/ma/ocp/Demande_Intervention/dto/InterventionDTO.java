package ma.ocp.Demande_Intervention.dto;




import java.util.Date;

import lombok.Data;
import ma.ocp.Demande_Intervention.enums.StatutIntervention;
@Data
public class InterventionDTO {
	 private Long id;
	    private Date dateDebut;
	    private Date dateFin;
	    private StatutIntervention statut;
	    private Long tempsPasse;
	    private String diagnostics;
	    private String solutionApporte;
	    private String commentaires;
	    private Boolean valideParDemandeur;
	    private Long demandeInterventionId;
	    private Long technicienId;
	    private Date dateIntervention;
}
