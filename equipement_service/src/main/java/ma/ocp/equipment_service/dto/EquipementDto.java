package ma.ocp.equipment_service.dto;

import lombok.*;
import ma.ocp.equipment_service.enums.Statut;
import ma.ocp.equipment_service.enums.TypeEquipement;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipementDto {
	private Long id;
    private String numeroSerie;
    private String nom;
    private String modele;
    private String marque;
    private String localisation;
    private TypeEquipement typeEquipement;
    private Statut statut;
    private Date dateAchat;
    private Date dateGarantie;
    private Long utilisateurId;
}