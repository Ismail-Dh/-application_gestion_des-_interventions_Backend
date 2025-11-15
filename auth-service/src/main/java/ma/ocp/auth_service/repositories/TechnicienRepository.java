package ma.ocp.auth_service.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.ocp.auth_service.entities.Technicien;

public interface TechnicienRepository extends JpaRepository<Technicien, Long> {

    List<Technicien> findByNiveau(Integer niveau);
    
    
}