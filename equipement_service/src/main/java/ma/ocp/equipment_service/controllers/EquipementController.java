package ma.ocp.equipment_service.controllers;

import lombok.RequiredArgsConstructor;
import ma.ocp.equipment_service.dto.EquipementDto;
import ma.ocp.equipment_service.services.EquipementService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipements")
@RequiredArgsConstructor
public class EquipementController {

    private final EquipementService equipementService;

    @PostMapping
    public EquipementDto ajouterEquipement(@RequestBody EquipementDto dto) {
        return equipementService.ajouterEquipement(dto);
    }

    @PutMapping("/{id}")
    public EquipementDto modifierEquipement(@PathVariable("id") Long id, @RequestBody EquipementDto dto) {
        return equipementService.modifierEquipement(id, dto);
    }

    @DeleteMapping("/{id}")
    public void supprimerEquipement(@PathVariable("id") Long id) {
        equipementService.supprimerEquipement(id);
    }

    @GetMapping("/{id}")
    public EquipementDto getEquipement(@PathVariable("id") Long id) {
        return equipementService.getEquipementById(id);
    }

    @GetMapping
    public List<EquipementDto> getAllEquipements() {
        return equipementService.getAllEquipements();
    }

    @PutMapping("/{id}/assign/{userId}")
    public void assignEquipement(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
        equipementService.attribuerEquipement(id, userId);
    }

    @GetMapping("/{id}/warranty")
    public boolean verifierGarantie(@PathVariable("id") Long id) {
        return equipementService.verifierGarantie(id);
    }

    @GetMapping("/failure-rate/{type}")
    public double calculerTauxPanne(@PathVariable("type") String type) {
        return equipementService.calculerTauxPanne(type);
    }
    @GetMapping("/count")
    public ResponseEntity<Long> compterUtilisateurs() {
        long total = equipementService.compterEquipaments();
        return ResponseEntity.ok(total);
    }
    
    @GetMapping("/utilisateur/{utilisateurId}")
    public List<EquipementDto> getEquipementsByUtilisateurId(@PathVariable Long utilisateurId) {
        return equipementService.getEquipementsByUtilisateurId(utilisateurId);
    }
    
    @PutMapping("/{id}/hors-service")
    public EquipementDto mettreHorsService(@PathVariable Long id) {
        return equipementService.mettreHorsService(id);
    }
    
    @PutMapping("/{id}/en-service")
    public EquipementDto mettreEnService(@PathVariable Long id) {
        return equipementService.mettreEnService(id);
    }

}
