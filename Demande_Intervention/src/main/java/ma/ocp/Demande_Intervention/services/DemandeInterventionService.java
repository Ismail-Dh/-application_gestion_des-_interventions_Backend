package ma.ocp.Demande_Intervention.services;
import java.util.List;

import ma.ocp.Demande_Intervention.dto.DemandeInterventionDTO;
import ma.ocp.Demande_Intervention.entities.Intervention;

public interface DemandeInterventionService {
    DemandeInterventionDTO save(DemandeInterventionDTO dto);
    DemandeInterventionDTO findById(Long id);
    List<DemandeInterventionDTO> findAll();
    void delete(Long id);
    DemandeInterventionDTO update(DemandeInterventionDTO dto);
    List<DemandeInterventionDTO> getDemandesByUtilisateurId(Long utilisateurId);
    DemandeInterventionDTO rejeterDemande(Long id) throws Exception;
    List<Intervention> getInterventionsByUtilisateur(Long utilisateurId);
    List<Intervention> getInterventionsByEquipement(Long equipementId);
}