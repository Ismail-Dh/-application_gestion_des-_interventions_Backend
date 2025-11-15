package ma.ocp.equipment_service.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.ocp.equipment_service.entities.Equipement;
@Repository

public interface EquipementRepository extends JpaRepository<Equipement, Long> {
	 long count();
	 List<Equipement> findByUtilisateurId(Long utilisateurId);
}
