 package ma.ocp.Demande_Intervention.entities;



import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import ma.ocp.Demande_Intervention.enums.StatutIntervention;

@Entity
@Data
public class Intervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateIntervention;
    private Date dateDebut;
    private Date dateFin;
    private Long tempsPasse;
    private String diagnostics;
    private String commentaires;
    private String solutionApporte;
    private Boolean valideParDemandeur;
    private Long technicienId;
    @Enumerated(EnumType.STRING)
    private StatutIntervention statut; 
    @ManyToOne
    private DemandeIntervention demandeIntervention;
}