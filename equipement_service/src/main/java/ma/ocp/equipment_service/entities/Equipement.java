package ma.ocp.equipment_service.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import ma.ocp.equipment_service.enums.Statut;
import ma.ocp.equipment_service.enums.TypeEquipement;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Equipement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String numeroSerie;

    private String nom;
    private String modele;
    private String marque;
    private String localisation;

    @Enumerated(EnumType.STRING)
    private TypeEquipement typeEquipement;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    private Date dateAchat;
    private Date dateGarantie;

    private Long utilisateurId; 
}
