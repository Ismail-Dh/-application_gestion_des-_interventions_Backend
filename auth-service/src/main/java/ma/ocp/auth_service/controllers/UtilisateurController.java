package ma.ocp.auth_service.controllers;

import lombok.RequiredArgsConstructor;
import ma.ocp.auth_service.dto.DemandeurDTO;
import ma.ocp.auth_service.dto.ResponsableDTO;
import ma.ocp.auth_service.dto.TechnicienDTO;
import ma.ocp.auth_service.dto.UtilisateurDTO;
import ma.ocp.auth_service.services.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    //  Créer un utilisateur
    @PostMapping
    public ResponseEntity<UtilisateurDTO> creerUtilisateur(@RequestBody UtilisateurDTO dto) {
        UtilisateurDTO created = utilisateurService.creerUtilisateur(dto);
        return ResponseEntity.ok(created);
    }

    //  Modifier un utilisateur
    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> modifierUtilisateur(@PathVariable("id")  Long id,
                                                              @RequestBody UtilisateurDTO dto) {
        UtilisateurDTO updated = utilisateurService.modifierUtilisateur(id, dto);
        return ResponseEntity.ok(updated);
    }

    //  Obtenir un utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> getUtilisateurById(@PathVariable("id") Long id) {
        UtilisateurDTO utilisateur = utilisateurService.getUtilisateurById(id);
        if (utilisateur == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(utilisateur);
    }

    //  Obtenir tous les utilisateurs
    @GetMapping
    public ResponseEntity<List<UtilisateurDTO>> getAllUtilisateurs() {
        return ResponseEntity.ok(utilisateurService.getAllUtilisateurs());
    }
    @GetMapping("/demandeurs")
    public List<DemandeurDTO> getAllDemandeurs() {
        return utilisateurService.getAllDemandeurs();
    }

    @GetMapping("/responsables")
    public List<ResponsableDTO> getAllResponsables() {
        return utilisateurService.getAllResponsables();
    }

    @GetMapping("/techniciens")
    public List<TechnicienDTO> getAllTechniciens() {
        return utilisateurService.getAllTechniciens();
    }
    //  Supprimer un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerUtilisateur(@PathVariable("id") Long id) {
        utilisateurService.supprimerUtilisateur(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/changer-statut")
    public ResponseEntity<String> changerStatut(@PathVariable("id") Long id) {
        utilisateurService.changerStatut(id);
        return ResponseEntity.ok("Statut modifié avec succès.");
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> compterUtilisateurs() {
        long total = utilisateurService.compterUtilisateurs();
        return ResponseEntity.ok(total);
    }
    
    @GetMapping("/demandeur/{id}")
    public DemandeurDTO getDemandeurById(@PathVariable("id") Long id) {
        return utilisateurService.getDemandeurById(id);
    }

    @GetMapping("/responsable/{id}")
    public ResponsableDTO getResponsableById(@PathVariable("id") Long id) {
        return utilisateurService.getResponsableById(id);
    }

    @GetMapping("/technicien/{id}")
    public TechnicienDTO getTechnicienById(@PathVariable("id") Long id) {
        return utilisateurService.getTechnicienById(id);
    }

    @GetMapping("/niveau/{niveau}")
    public List<TechnicienDTO> getTechniciensParNiveau(@PathVariable("niveau") Integer niveau) {
        return utilisateurService.getTechniciensByNiveau(niveau);
    }
}
