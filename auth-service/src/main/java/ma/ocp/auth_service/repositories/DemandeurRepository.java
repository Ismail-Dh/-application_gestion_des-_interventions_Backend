package ma.ocp.auth_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.ocp.auth_service.entities.Demandeur;

public interface DemandeurRepository extends JpaRepository<Demandeur, Long> {

}
