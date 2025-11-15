package ma.ocp.equipment_service.services;

import ma.ocp.equipment_service.dto.EquipementDto;
import java.util.List;

public interface EquipementService {
    EquipementDto ajouterEquipement(EquipementDto equipementDto);
    EquipementDto modifierEquipement(Long id, EquipementDto equipementDto);
    void supprimerEquipement(Long id);
    EquipementDto getEquipementById(Long id);
    List<EquipementDto> getAllEquipements();
    void attribuerEquipement(Long idEquipement, Long utilisateurId);
    boolean verifierGarantie(Long idEquipement);
    double calculerTauxPanne(String typeEquipement);
    long compterEquipaments() ;
    List<EquipementDto> getEquipementsByUtilisateurId(Long utilisateurId);
    EquipementDto mettreHorsService(Long idEquipement);
    EquipementDto mettreEnService(Long idEquipement);

}
