package ma.ocp.auth_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.ocp.auth_service.entities.Administrateur;

public interface AdministrateurRepository extends JpaRepository<Administrateur, Long> {
   
}