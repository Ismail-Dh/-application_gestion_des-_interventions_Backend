package ma.ocp.auth_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.ocp.auth_service.entities.Responsable;

public interface ResponsableRepository extends JpaRepository<Responsable, Long> {
    
}