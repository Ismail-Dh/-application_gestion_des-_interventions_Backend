package ma.ocp.Demande_Intervention.services;

import java.util.List;
import java.util.Optional;

import ma.ocp.Demande_Intervention.dto.InterventionDTO;
import ma.ocp.Demande_Intervention.entities.Intervention;

public interface InterventionService {
	InterventionDTO saveIntervention(InterventionDTO dto);
    InterventionDTO getInterventionById(Long id);
    List<InterventionDTO> getAllInterventions();
    void deleteIntervention(Long id);
    Optional<Intervention> updateIntervention(Long id, Intervention updated) ;
    List<InterventionDTO> getInterventionsByTechnicien(Long technicienId);
    InterventionDTO escaladerIntervention(Long id);
    InterventionDTO validerParDemandeur(Long id);
    Optional<Intervention> findById(Long id);
    List<String> getSolutionsByProbleme(String probleme);
}
