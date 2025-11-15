package ma.ocp.auth_service.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.ocp.auth_service.entities.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    
    Optional<Utilisateur> findByMatricule(String matricule);
    
    Optional<Utilisateur> findByEmail(String email);
    
    boolean existsByMatricule(String matricule);
    
    boolean existsByEmail(String email);
    
    long count();

}