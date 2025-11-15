package ma.ocp.equipment_service.services_impl;

import lombok.RequiredArgsConstructor;
import ma.ocp.equipment_service.dto.EquipementDto;
import ma.ocp.equipment_service.entities.Equipement;
import ma.ocp.equipment_service.enums.Statut;
import ma.ocp.equipment_service.repositories.EquipementRepository;
import ma.ocp.equipment_service.services.EquipementService;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements EquipementService {

    private final EquipementRepository equipementRepository;

    @Override
    public EquipementDto ajouterEquipement(EquipementDto dto) {
        Equipement equipement = dtoToEntity(dto);
        return entityToDto(equipementRepository.save(equipement));
    }

    @Override
    public EquipementDto modifierEquipement(Long id, EquipementDto dto) {
        Equipement equipement = equipementRepository.findById(id).orElseThrow();
        equipement.setNom(dto.getNom());
        equipement.setModele(dto.getModele());
        equipement.setMarque(dto.getMarque());
        equipement.setLocalisation(dto.getLocalisation());
        equipement.setStatut(dto.getStatut());
        equipement.setTypeEquipement(dto.getTypeEquipement());
        equipement.setDateAchat(dto.getDateAchat());
        equipement.setDateGarantie(dto.getDateGarantie());
        equipement.setUtilisateurId(dto.getUtilisateurId());
        return entityToDto(equipementRepository.save(equipement));
    }

    @Override
    public void supprimerEquipement(Long id) {
        equipementRepository.deleteById(id);
    }

    @Override
    public EquipementDto getEquipementById(Long id) {
        return equipementRepository.findById(id).map(this::entityToDto).orElse(null);
    }

    @Override
    public List<EquipementDto> getAllEquipements() {
        return equipementRepository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public void attribuerEquipement(Long idEquipement, Long utilisateurId) {
        Equipement equipement = equipementRepository.findById(idEquipement).orElseThrow();
        equipement.setUtilisateurId(utilisateurId);
        equipementRepository.save(equipement);
    }

    @Override
    public boolean verifierGarantie(Long idEquipement) {
        Equipement equipement = equipementRepository.findById(idEquipement).orElseThrow();
        return equipement.getDateGarantie().after(new Date());
    }

    @Override
    public double calculerTauxPanne(String typeEquipement) {
        List<Equipement> all = equipementRepository.findAll();
        long total = all.stream().filter(e -> e.getTypeEquipement().name().equalsIgnoreCase(typeEquipement)).count();
        long panne = all.stream().filter(e -> e.getTypeEquipement().name().equalsIgnoreCase(typeEquipement)
                && e.getStatut() == Statut.HORS_SERVICE).count();
        return total == 0 ? 0 : (double) panne / total * 100;
    }

    private EquipementDto entityToDto(Equipement equipement) {
        return EquipementDto.builder()
        		.id(equipement.getId())
                .numeroSerie(equipement.getNumeroSerie())
                .nom(equipement.getNom())
                .modele(equipement.getModele())
                .marque(equipement.getMarque())
                .localisation(equipement.getLocalisation())
                .typeEquipement(equipement.getTypeEquipement())
                .statut(equipement.getStatut())
                .dateAchat(equipement.getDateAchat())
                .dateGarantie(equipement.getDateGarantie())
                .utilisateurId(equipement.getUtilisateurId())
                .build();
    }

    private Equipement dtoToEntity(EquipementDto dto) {
        return Equipement.builder()
                .numeroSerie(dto.getNumeroSerie())
                .nom(dto.getNom())
                .modele(dto.getModele())
                .marque(dto.getMarque())
                .localisation(dto.getLocalisation())
                .typeEquipement(dto.getTypeEquipement())
                .statut(dto.getStatut())
                .dateAchat(dto.getDateAchat())
                .dateGarantie(dto.getDateGarantie())
                .utilisateurId(dto.getUtilisateurId())
                .build();
    }
    
    @Override
    public List<EquipementDto> getEquipementsByUtilisateurId(Long utilisateurId) {
        List<Equipement> equipements = equipementRepository.findByUtilisateurId(utilisateurId);
        return equipements.stream()
                          .map(this::entityToDto)
                          .collect(Collectors.toList());
    }


	@Override
	public long compterEquipaments() {
		// TODO Auto-generated method stub
		return equipementRepository.count();
	}
	
	@Override
	public EquipementDto mettreHorsService(Long idEquipement) {
	    Equipement equipement = equipementRepository.findById(idEquipement)
	            .orElseThrow(() -> new RuntimeException("Équipement introuvable avec l'id: " + idEquipement));
	    
	    equipement.setStatut(Statut.HORS_SERVICE); // ⚡ Met à hors service
	    Equipement updated = equipementRepository.save(equipement);
	    
	    return entityToDto(updated);
	}

	@Override
	public EquipementDto mettreEnService(Long idEquipement) {
		Equipement equipement = equipementRepository.findById(idEquipement)
	            .orElseThrow(() -> new RuntimeException("Équipement introuvable avec l'id: " + idEquipement));
	    
	    equipement.setStatut(Statut.EN_SERVICE); // ⚡ Met à hors service
	    Equipement updated = equipementRepository.save(equipement);
	    
	    return entityToDto(updated);
	}

 }