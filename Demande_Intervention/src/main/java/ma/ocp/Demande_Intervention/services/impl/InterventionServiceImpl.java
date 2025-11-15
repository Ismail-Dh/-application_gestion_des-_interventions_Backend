package ma.ocp.Demande_Intervention.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.ocp.Demande_Intervention.dto.InterventionDTO;
import ma.ocp.Demande_Intervention.entities.DemandeIntervention;
import ma.ocp.Demande_Intervention.entities.Intervention;
import ma.ocp.Demande_Intervention.enums.StatutDemande;
import ma.ocp.Demande_Intervention.enums.StatutIntervention;
import ma.ocp.Demande_Intervention.repositories.DemandeInterventionRepository;
import ma.ocp.Demande_Intervention.repositories.InterventionRepository;
import ma.ocp.Demande_Intervention.services.InterventionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterventionServiceImpl implements InterventionService {

    private final InterventionRepository interventionRepository;
    private final DemandeInterventionRepository demandeRepo;

    @Autowired
    public InterventionServiceImpl(InterventionRepository interventionRepository,
                                   DemandeInterventionRepository demandeRepo) {
        this.interventionRepository = interventionRepository;
        this.demandeRepo = demandeRepo;
    }
    @Override
    public Optional<Intervention> findById(Long id) {
        return interventionRepository.findById(id);
    }

    @Override
    public InterventionDTO saveIntervention(InterventionDTO dto) {
        DemandeIntervention demande = demandeRepo.findById(dto.getDemandeInterventionId())
                .orElseThrow(() -> new RuntimeException("Demande non trouvée"));

        Intervention intervention = new Intervention();
        BeanUtils.copyProperties(dto, intervention);
        intervention.setDemandeIntervention(demande);
        intervention.setStatut(StatutIntervention.PLANIFIEE);
        intervention.setDateIntervention(new Date());
        Intervention saved = interventionRepository.save(intervention);
         demande.setStatut(StatutDemande.VALIDEE);
         demandeRepo.save(demande);
        // Mettre à jour l’ID généré dans le DTO
        dto.setId(saved.getId());
        return dto;
    }

    @Override
    public InterventionDTO getInterventionById(Long id) {
        Intervention i = interventionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Intervention non trouvée"));

        InterventionDTO dto = new InterventionDTO();
        BeanUtils.copyProperties(i, dto);

        // Lier l’ID de la demande
        if (i.getDemandeIntervention() != null) {
            dto.setDemandeInterventionId(i.getDemandeIntervention().getId());
        }

        return dto;
    }

    @Override
    public List<InterventionDTO> getAllInterventions() {
        return interventionRepository.findAll().stream().map(i -> {
            InterventionDTO dto = new InterventionDTO();
            BeanUtils.copyProperties(i, dto);
            if (i.getDemandeIntervention() != null) {
                dto.setDemandeInterventionId(i.getDemandeIntervention().getId());
            }
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteIntervention(Long id) {
        interventionRepository.deleteById(id);
    }
    
    public Optional<Intervention> updateIntervention(Long id, Intervention updated) {
        return interventionRepository.findById(id).map(intervention -> {
            if (updated.getDateDebut() != null) {
                intervention.setDateDebut(updated.getDateDebut());
            }
            if (updated.getTechnicienId() != null) {
                intervention.setTechnicienId(updated.getTechnicienId());
            }
            if (updated.getDiagnostics() != null) {
                intervention.setDiagnostics(updated.getDiagnostics());
            }
            if (updated.getCommentaires() != null) {
                intervention.setCommentaires(updated.getCommentaires());
            }
            if (updated.getSolutionApporte() != null) {
                intervention.setSolutionApporte(updated.getSolutionApporte());
            }
            if (updated.getDateFin() != null) {
                intervention.setDateFin(updated.getDateFin());
            }
            if (updated.getTempsPasse()!= null) {
                intervention.setTempsPasse(updated.getTempsPasse());
            }
            if (updated.getStatut()!= null) {
                intervention.setStatut(updated.getStatut());
            }
            
            return interventionRepository.save(intervention);
        });
    }
    
    public List<InterventionDTO> getInterventionsByTechnicien(Long technicienId) {
        List<Intervention> interventions = interventionRepository.findByTechnicienId(technicienId);
        return interventions.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    
    
    @Scheduled(fixedRate = 60000) // toutes les 60 secondes
    @Transactional
    public void verifierDebutInterventions() {
        Date maintenant = new Date();
        List<Intervention> interventions = interventionRepository.findAll();
        
        interventions.stream()
            .filter(i -> i.getDateDebut() != null
                      && i.getDateDebut().before(maintenant)
                      && i.getStatut() == StatutIntervention.PLANIFIEE)
            .forEach(i -> {
                i.setStatut(StatutIntervention.EN_COURS);
                interventionRepository.save(i);
            });
    }
    
    
    @Transactional
    public InterventionDTO escaladerIntervention(Long id) {
        Intervention intervention = interventionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Intervention non trouvée"));

        intervention.setStatut(StatutIntervention.ESCALADEE);
        Intervention saved = interventionRepository.save(intervention);

        return mapToDTO(saved);
    }
    @Transactional
    public InterventionDTO validerParDemandeur(Long id) {
        Intervention intervention = interventionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Intervention non trouvée"));

        // Marquer comme validée par le demandeur
        intervention.setValideParDemandeur(true);

        // Changer le statut à TERMINEE
        intervention.setStatut(StatutIntervention.TERMINEE);

        // Sauvegarder l’intervention
        Intervention saved = interventionRepository.save(intervention);

        return mapToDTO(saved);
    }

    private InterventionDTO mapToDTO(Intervention intervention) {
        InterventionDTO dto = new InterventionDTO();
        dto.setId(intervention.getId());
        dto.setDateDebut(intervention.getDateDebut());
        dto.setDateFin(intervention.getDateFin());
        dto.setStatut(intervention.getStatut());
        dto.setTempsPasse(intervention.getTempsPasse());
        dto.setDiagnostics(intervention.getDiagnostics());
        dto.setSolutionApporte(intervention.getSolutionApporte());
        dto.setCommentaires(intervention.getCommentaires());
        dto.setValideParDemandeur(intervention.getValideParDemandeur());
        dto.setDemandeInterventionId(intervention.getDemandeIntervention().getId());
        dto.setTechnicienId(intervention.getTechnicienId());
        dto.setDateIntervention(intervention.getDateIntervention());
        return dto;
    }
    
    public List<String> getSolutionsByProbleme(String probleme) {
        return interventionRepository.findByDiagnosticsContainingIgnoreCase(probleme)
                                     .stream()
                                     .map(Intervention::getSolutionApporte)
                                     .filter(Objects::nonNull)
                                     .toList();
    }


}