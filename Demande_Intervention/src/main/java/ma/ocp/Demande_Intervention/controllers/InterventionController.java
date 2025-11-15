package ma.ocp.Demande_Intervention.controllers;


import lombok.RequiredArgsConstructor;
import ma.ocp.Demande_Intervention.dto.DemandeInterventionDTO;
import ma.ocp.Demande_Intervention.dto.InterventionDTO;
import ma.ocp.Demande_Intervention.entities.DemandeIntervention;
import ma.ocp.Demande_Intervention.entities.Intervention;
import ma.ocp.Demande_Intervention.messaging.NotifSender;
import ma.ocp.Demande_Intervention.services.DemandeInterventionService;
import ma.ocp.Demande_Intervention.services.InterventionService;
import ma.ocp.Notification.dto.NotificationRequestDTO;
import ma.ocp.Notification.enums.NotificationType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/interventions")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class InterventionController {
    @Autowired
     private InterventionService interventionService;
    @Autowired
    private DemandeInterventionService demandeInterventionService ;
    @Autowired
    private final NotifSender notifSender;
    
    
    // ✅ Créer une intervention
    @PostMapping
  
    public ResponseEntity<InterventionDTO> createIntervention(@RequestBody InterventionDTO interventionDTO) {
        InterventionDTO created = interventionService.saveIntervention(interventionDTO);
        DemandeInterventionDTO demande = demandeInterventionService.findById(interventionDTO.getDemandeInterventionId());

        // Construction de la notification
        NotificationRequestDTO notif = new NotificationRequestDTO();
        notif.setUserId(demande.getUtilisateurId()); // depuis ton DTO
        notif.setMessage("Félicitations ! Votre demande a été acceptée.");
        notif.setTitle("Demande Acceptée");
        notif.setType(NotificationType.ACCEPTER);

        notifSender.envoyerLog(notif);
        
        NotificationRequestDTO notif1 = new NotificationRequestDTO();
        notif1.setUserId(created.getTechnicienId()); // depuis ton DTO
        notif1.setMessage("Une nouvelle intervention vous a été assignée. Merci de la consulter et de prendre en charge la mission dès que possible.");
        notif1.setTitle("Nouvelle intervention");
        notif1.setType(NotificationType.INTERVENTION_ASSIGNED);

        notifSender.envoyerLog(notif1);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // ✅ Lire une intervention par ID
    @GetMapping("/{id}")
    public InterventionDTO getById(@PathVariable Long id) {
        return interventionService.getInterventionById(id);
    }

    // ✅ Lire toutes les interventions
    @GetMapping
    public List<InterventionDTO> getAll() {
        return interventionService.getAllInterventions();
    }

    // ✅ Supprimer une intervention
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        interventionService.deleteIntervention(id);
    }

    // ✅ (Optionnel) Mettre à jour une intervention
    @PutMapping("/{id}")
    public InterventionDTO update(@PathVariable Long id, @RequestBody InterventionDTO dto) {
        dto.setId(id);
        return interventionService.saveIntervention(dto); // même méthode que pour la création
    }
    
    @PutMapping("/interventions/{id}")
    public ResponseEntity<Intervention> updateIntervention(
            @PathVariable Long id,
            @RequestBody Intervention updatedIntervention) {
    	
    	
    	
        return interventionService.updateIntervention(id, updatedIntervention)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/interventions/completer/{id}")
    public ResponseEntity<Intervention> updateIntervention1(
            @PathVariable Long id,
            @RequestBody Intervention updatedIntervention) {
    	
    	Intervention intervention = interventionService.findById(id)
    		    .orElseThrow(() -> new RuntimeException("Intervention introuvable"));

    		DemandeIntervention demande = intervention.getDemandeIntervention();
    		if (demande != null) {
    		    NotificationRequestDTO notif = new NotificationRequestDTO();
    		    notif.setUserId(demande.getUtilisateurId());
    		    notif.setMessage("L’intervention liée à votre demande est terminée. Merci de vérifier et de valider l’intervention pour clôturer la demande.");
    		    notif.setTitle("Demande de validation");
    		    notif.setType(NotificationType.VALIDER);
    		    notifSender.envoyerLog(notif);
    		}
    	
        return interventionService.updateIntervention(id, updatedIntervention)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    
    
    @GetMapping("/technicien/{id}")
    public List<InterventionDTO> getInterventionsByTechnicien(@PathVariable("id") Long id) {
        return interventionService.getInterventionsByTechnicien(id);
    }
    
    @PutMapping("/{id}/escalader")
    public ResponseEntity<InterventionDTO> escalader(@PathVariable Long id) {
        InterventionDTO dto = interventionService.escaladerIntervention(id);
        NotificationRequestDTO notif = new NotificationRequestDTO();
    	int id1 = 1;
    	notif.setUserId((long) id1);
    	notif.setMessage("Une intervention a été escaladée vers un niveau supérieur pour traitement.Merci de prendre les mesures nécessaires dans les plus brefs délais.");
    	notif.setTitle("Intervention escaladée");
    	notif.setType(NotificationType.ESCALADER);
    	
    	notifSender.envoyerLog(notif);
        return ResponseEntity.ok(dto);
    }
    // Endpoint pour valider par le demandeur
    @PutMapping("/{id}/valider")
    public ResponseEntity<InterventionDTO> validerParDemandeur(@PathVariable Long id) {
        InterventionDTO updatedIntervention = interventionService.validerParDemandeur(id);
        Intervention intervention = interventionService.findById(id)
    		    .orElseThrow(() -> new RuntimeException("Intervention introuvable"));

    		
    		
    		    NotificationRequestDTO notif = new NotificationRequestDTO();
    		    notif.setUserId(intervention.getTechnicienId());
    		    notif.setMessage("Votre intervention a été validée par le demandeur et est maintenant clôturée. Merci pour votre travail !");
    		    notif.setTitle("Intervention clôturée");
    		    notif.setType(NotificationType.INTERVENTION_CLOSED);
    		    notifSender.envoyerLog(notif);
    		

        return ResponseEntity.ok(updatedIntervention);
    }
    
    @GetMapping("/solutions")
    public ResponseEntity<List<String>> getSolutionsParProbleme(@RequestParam String probleme) {
        List<String> solutions = interventionService.getSolutionsByProbleme(probleme);
        return ResponseEntity.ok(solutions);
    }

}
