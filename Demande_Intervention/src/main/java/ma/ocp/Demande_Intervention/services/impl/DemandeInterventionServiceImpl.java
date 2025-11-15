package ma.ocp.Demande_Intervention.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.ocp.Demande_Intervention.dto.DemandeInterventionDTO;
import ma.ocp.Demande_Intervention.entities.DemandeIntervention;
import ma.ocp.Demande_Intervention.entities.Intervention;
import ma.ocp.Demande_Intervention.enums.StatutDemande;
import ma.ocp.Demande_Intervention.repositories.DemandeInterventionRepository;
import ma.ocp.Demande_Intervention.repositories.InterventionRepository;
import ma.ocp.Demande_Intervention.services.DemandeInterventionService;


@Service
public class DemandeInterventionServiceImpl implements DemandeInterventionService {

    @Autowired
    private DemandeInterventionRepository repository;
    @Autowired
    private InterventionRepository interventionRepository;


    @Override
    public DemandeInterventionDTO save(DemandeInterventionDTO dto) {
        DemandeIntervention entity = new DemandeIntervention();
        BeanUtils.copyProperties(dto, entity);
        entity.setDateDemande(new Date());
        if (dto.getStatut() != null)
            entity.setStatut(StatutDemande.valueOf(dto.getStatut()));
        else
            entity.setStatut(StatutDemande.EN_ATTENTE);
     // Priorité
        if (dto.getPriorite() != null)
            entity.setPriorite(ma.ocp.Demande_Intervention.enums.Priorite.valueOf(dto.getPriorite()));
        else
            entity.setPriorite(ma.ocp.Demande_Intervention.enums.Priorite.MOYENNE);

        // Niveau d'intervention
        if (dto.getNiveauIntervention() != null)
            entity.setNiveauIntervention(ma.ocp.Demande_Intervention.enums.NiveauIntervention.valueOf(dto.getNiveauIntervention()));
        else
            entity.setNiveauIntervention(ma.ocp.Demande_Intervention.enums.NiveauIntervention.NIVEAU_1);

        
        return toDTO(repository.save(entity));
        
        
    
    }

    @Override
    public DemandeInterventionDTO findById(Long id) {
        return repository.findById(id).map(this::toDTO).orElse(null);
    }

    @Override
    public List<DemandeInterventionDTO> findAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private DemandeInterventionDTO toDTO(DemandeIntervention entity) {
        DemandeInterventionDTO dto = new DemandeInterventionDTO();
        BeanUtils.copyProperties(entity, dto);
        if (entity.getStatut() != null)
            dto.setStatut(entity.getStatut().name());
        if (entity.getStatut() != null)
            dto.setStatut(entity.getStatut().name());

        if (entity.getPriorite() != null)
            dto.setPriorite(entity.getPriorite().name());

        if (entity.getNiveauIntervention() != null)
            dto.setNiveauIntervention(entity.getNiveauIntervention().name());

        return dto;
    }
  
    public DemandeInterventionDTO update(DemandeInterventionDTO dto) {
        DemandeIntervention entity = repository.findById(dto.getId()).orElse(null);
        if (entity == null) return null;

        // Met à jour les champs existants
        BeanUtils.copyProperties(dto, entity, "id");

        // Définit les valeurs par défaut si null
        if (dto.getStatut() != null)
            entity.setStatut(StatutDemande.valueOf(dto.getStatut()));
        else if (entity.getStatut() == null)
            entity.setStatut(StatutDemande.EN_ATTENTE);

        if (dto.getPriorite() != null)
            entity.setPriorite(ma.ocp.Demande_Intervention.enums.Priorite.valueOf(dto.getPriorite()));
        else if (entity.getPriorite() == null)
            entity.setPriorite(ma.ocp.Demande_Intervention.enums.Priorite.MOYENNE);

        if (dto.getNiveauIntervention() != null)
            entity.setNiveauIntervention(ma.ocp.Demande_Intervention.enums.NiveauIntervention.valueOf(dto.getNiveauIntervention()));
        else if (entity.getNiveauIntervention() == null)
            entity.setNiveauIntervention(ma.ocp.Demande_Intervention.enums.NiveauIntervention.NIVEAU_1);

        return toDTO(repository.save(entity));
    }
    public List<DemandeInterventionDTO> getDemandesByUtilisateurId(Long utilisateurId) {
        // On récupère la liste des entités depuis la base
        List<DemandeIntervention> demandes = repository.findByUtilisateurId(utilisateurId);
        
        // On convertit la liste d'entités en liste de DTOs
        return demandes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    public DemandeInterventionDTO rejeterDemande(Long id) throws Exception {
        DemandeIntervention demande = repository.findById(id)
            .orElseThrow(() -> new Exception("Demande non trouvée avec l'id : " + id));

        demande.setStatut(StatutDemande.REJETEE);

        DemandeIntervention saved = repository.save(demande);
        

        return toDTO(saved);
    }
    
    public List<Intervention> getInterventionsByUtilisateur(Long utilisateurId) {
        List<DemandeIntervention> demandes = repository.findByUtilisateurId(utilisateurId);

        List<Long> demandeIds = demandes.stream()
            .filter(d -> d.getStatut() != StatutDemande.REJETEE)
            .map(DemandeIntervention::getId)
            .collect(Collectors.toList());

        if (demandeIds.isEmpty()) return List.of();

        return interventionRepository.findByDemandeIntervention_IdIn(demandeIds);
    }
    public List<Intervention> getInterventionsByEquipement(Long equipementId) {
        // Récupérer toutes les demandes liées à cet équipement
        List<DemandeIntervention> demandes = repository.findByEquipementId(equipementId);

        // Filtrer celles qui ne sont pas rejetées et récupérer les IDs
        List<Long> demandeIds = demandes.stream()
            .filter(d -> d.getStatut() != StatutDemande.REJETEE)
            .map(DemandeIntervention::getId)
            .collect(Collectors.toList());

        if (demandeIds.isEmpty()) return List.of();

        // Retourner toutes les interventions liées à ces demandes
        return interventionRepository.findByDemandeIntervention_IdIn(demandeIds);
    }

}