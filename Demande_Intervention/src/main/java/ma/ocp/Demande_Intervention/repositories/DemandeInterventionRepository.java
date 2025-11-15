package ma.ocp.Demande_Intervention.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.ocp.Demande_Intervention.entities.DemandeIntervention;

public interface DemandeInterventionRepository extends JpaRepository<DemandeIntervention, Long> {
	List<DemandeIntervention> findByUtilisateurId(Long utilisateurId);
	List<DemandeIntervention> findByEquipementId(Long equipementId);

}