package ma.ocp.Demande_Intervention.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.ocp.Demande_Intervention.entities.Intervention;


public interface InterventionRepository extends JpaRepository<Intervention, Long> {
	 List<Intervention> findByTechnicienId(Long technicienId);
	 List<Intervention> findByDemandeIntervention_IdIn(List<Long> demandeIds);
	 List<Intervention> findByDiagnosticsContainingIgnoreCase(String diagnostics);
	 
}