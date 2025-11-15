package ma.ocp.Demande_Intervention.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ma.ocp.Demande_Intervention.dto.DemandeInterventionDTO;
import ma.ocp.Demande_Intervention.entities.Intervention;
import ma.ocp.Demande_Intervention.messaging.NotifSender;
import ma.ocp.Demande_Intervention.services.DemandeInterventionService;
import ma.ocp.Notification.dto.NotificationRequestDTO;
import ma.ocp.Notification.enums.NotificationType;



@RestController
@RequestMapping("/api/Demandes")
@RequiredArgsConstructor
public class DemandeInterventionController {

    @Autowired
    private DemandeInterventionService service;
    @Autowired
    private final NotifSender notifSender;
    
    
    @PostMapping
    public DemandeInterventionDTO create(@RequestBody DemandeInterventionDTO dto) {
    	NotificationRequestDTO notif = new NotificationRequestDTO();
    	int id = 1;
    	notif.setUserId((long) id);
    	notif.setMessage("Une nouvelle demande vient d’être enregistrée. Merci de la consulter et de choisir l’action appropriée : Accepter ✅ ou Rejeter ❌");
    	notif.setTitle("Nouvelle demande");;
    	notif.setType(NotificationType.NOUVELLE);
    	
    	notifSender.envoyerLog(notif);
        return service.save(dto);
    }

    @GetMapping("/{id}")
    public DemandeInterventionDTO getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<DemandeInterventionDTO> getAll() {
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
   
    @PutMapping("/{id}")
    public DemandeInterventionDTO update(@PathVariable Long id, @RequestBody DemandeInterventionDTO dto) {
        dto.setId(id); // On s'assure que l'ID est bien défini
        return service.update(dto);
    }
    
    @GetMapping("/utilisateur/{utilisateurId}")
    public List<DemandeInterventionDTO> getDemandesByUtilisateur(@PathVariable Long utilisateurId) {
        return service.getDemandesByUtilisateurId(utilisateurId);
    }

    
    @PutMapping("/{id}/rejeter")
    public ResponseEntity<DemandeInterventionDTO> rejeterDemande(@PathVariable("id") Long id) {
        try {
            DemandeInterventionDTO dto = service.rejeterDemande(id);
            NotificationRequestDTO notif = new NotificationRequestDTO();
            notif.setUserId(dto.getUtilisateurId());
            notif.setMessage("Votre demande a été examinée et a malheureusement été rejetée.");
            notif.setTitle("Demande Rejeter");
            notif.setType(NotificationType.REJETER);
            notifSender.envoyerLog(notif);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/utilisateur/interventions/{utilisateurId}")
    public ResponseEntity<List<Intervention>> getInterventionsByUtilisateur(
            @PathVariable Long utilisateurId) {
        
        List<Intervention> interventions = service.getInterventionsByUtilisateur(utilisateurId);
        
        if (interventions.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 si aucune intervention
        }
        
        return ResponseEntity.ok(interventions);
    }
  
    @GetMapping("/equipement/interventions/{Id}")
    public ResponseEntity<List<Intervention>> getInterventionsByEquipemnt(
            @PathVariable Long Id) {
        
        List<Intervention> interventions = service.getInterventionsByEquipement(Id);
        
        if (interventions.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 si aucune intervention
        }
        
        return ResponseEntity.ok(interventions);
    }
}