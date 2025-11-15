package ma.ocp.auth_service.services;

import java.util.List;

import ma.ocp.auth_service.dto.DemandeurDTO;
import ma.ocp.auth_service.dto.ResponsableDTO;
import ma.ocp.auth_service.dto.TechnicienDTO;
import ma.ocp.auth_service.dto.UtilisateurDTO;

public interface UtilisateurService {
	 UtilisateurDTO creerUtilisateur(UtilisateurDTO dto);

	    UtilisateurDTO modifierUtilisateur(Long id, UtilisateurDTO dto);

	    UtilisateurDTO getUtilisateurById(Long id);

	    List<UtilisateurDTO> getAllUtilisateurs();
	    List<DemandeurDTO> getAllDemandeurs();
	    List<ResponsableDTO> getAllResponsables();
	    List<TechnicienDTO> getAllTechniciens();
	    void changerStatut(Long idUtilisateur);
	    void supprimerUtilisateur(Long id);
	    long compterUtilisateurs() ;
	    List<TechnicienDTO> getTechniciensByNiveau(Integer niveau);
	    DemandeurDTO getDemandeurById(Long id);
	    ResponsableDTO getResponsableById(Long id);
	    TechnicienDTO getTechnicienById(Long id);

	  
}
