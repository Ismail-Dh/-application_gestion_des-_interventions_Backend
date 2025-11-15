package ma.ocp.Demande_Intervention.entities;



import java.util.Date;
import jakarta.persistence.*;
import lombok.*;
import lombok.Data;
import ma.ocp.Demande_Intervention.enums.NiveauIntervention;
import ma.ocp.Demande_Intervention.enums.Priorite;
import ma.ocp.Demande_Intervention.enums.StatutDemande;
@Entity
@Data
public class DemandeIntervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDemande;
    @Enumerated(EnumType.STRING)
    private StatutDemande statut;
    @Enumerated(EnumType.STRING)
    private NiveauIntervention niveauIntervention;
    @Enumerated(EnumType.STRING)
    private Priorite priorite;
    private Long equipementId;
    private Long utilisateurId;
    private String lieu; 
}
