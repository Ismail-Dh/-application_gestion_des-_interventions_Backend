package ma.ocp.auth_service.services_impl;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import ma.ocp.auth_service.dto.DemandeurDTO;
import ma.ocp.auth_service.dto.ResponsableDTO;
import ma.ocp.auth_service.dto.TechnicienDTO;
import ma.ocp.auth_service.dto.UtilisateurDTO;
import ma.ocp.auth_service.entities.Administrateur;
import ma.ocp.auth_service.entities.Demandeur;
import ma.ocp.auth_service.entities.Responsable;
import ma.ocp.auth_service.entities.Technicien;
import ma.ocp.auth_service.entities.Utilisateur;
import ma.ocp.auth_service.repositories.DemandeurRepository;
import ma.ocp.auth_service.repositories.ResponsableRepository;
import ma.ocp.auth_service.repositories.TechnicienRepository;
import ma.ocp.auth_service.repositories.UtilisateurRepository;
import ma.ocp.auth_service.services.UtilisateurService;
import ma.ocp.auth_service.enums.UtilisateurType; 

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final DemandeurRepository demandeurRepository;
    private final ResponsableRepository responsableRepository;
    private final TechnicienRepository technicienRepository;
    private final PasswordEncoder passwordEncoder;
    

    @Override
    public UtilisateurDTO creerUtilisateur(UtilisateurDTO dto) {
        Utilisateur utilisateur = mapDtoToEntity(dto);
        utilisateur.setDateDeCreation(new Date());
        utilisateur.setStatu("ACTIF");
        utilisateur.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        utilisateurRepository.save(utilisateur);
        return mapEntityToDto(utilisateur);
    }

    @Override
    public UtilisateurDTO modifierUtilisateur(Long id, UtilisateurDTO dto) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow();

        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setMatricule(dto.getMatricule());
        utilisateur.setTelephone(dto.getTelephone());
        utilisateur.setEmail(dto.getEmail());
        utilisateur.setStatu(dto.getStatu());
        switch (utilisateur) {
            case Responsable resp when dto instanceof ResponsableDTO respDto -> {
                resp.setTypeService(respDto.getTypeService());
            }
            case Technicien tech when dto instanceof TechnicienDTO techDto -> {
                tech.setNiveau(techDto.getNiveau());
            }
            case Demandeur dem when dto instanceof DemandeurDTO demDto -> {
                dem.setDepartement(demDto.getDepartement());
            }
            default -> {
                
            }
        }

        utilisateurRepository.save(utilisateur);
        return mapEntityToDto(utilisateur);
    }
    @Override
    public long compterUtilisateurs() {
        return utilisateurRepository.count();
    }

    @Override
    public UtilisateurDTO getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id)
                .map(this::mapEntityToDto)
                .orElse(null);
    }

    @Override
    public List<UtilisateurDTO> getAllUtilisateurs() {
        return utilisateurRepository.findAll().stream()
        	    .map(this::mapEntityToDto)
        	    .toList();

    }

    @Override
    public void supprimerUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }
    
    @Override
    public List<DemandeurDTO> getAllDemandeurs() {
        return demandeurRepository.findAll().stream().map(this::mapToDemandeurDTO).toList();
    }

    @Override
    public List<ResponsableDTO> getAllResponsables() {
        return responsableRepository.findAll().stream().map(this::mapToResponsableDTO).toList();
    }

    @Override
    public List<TechnicienDTO> getAllTechniciens() {
        return technicienRepository.findAll().stream().map(this::mapToTechnicienDTO).toList();
    }

    private Utilisateur mapDtoToEntity(UtilisateurDTO dto) {
        switch (dto.getType()) {
            case UtilisateurType.ADMINISTRATEUR:
                Administrateur admin = new Administrateur();
                fillBaseFields(admin, dto);
                return admin;
            case UtilisateurType.RESPONSABLE:
                Responsable resp = new Responsable();
                fillBaseFields(resp, dto);
                if (dto instanceof ResponsableDTO respDto) {
                    resp.setTypeService(respDto.getTypeService());
                }
                return resp;
            case UtilisateurType.TECHNICIEN:
                Technicien tech = new Technicien();
                fillBaseFields(tech, dto);
                if (dto instanceof TechnicienDTO techDto) {
                    tech.setNiveau(techDto.getNiveau());
                }
                return tech;
            default:
                Demandeur user = new Demandeur();
                fillBaseFields(user, dto);
                if (dto instanceof DemandeurDTO demDto) {
                    user.setDepartement(demDto.getDepartement());
                }
                return user;
        }
    }

    private void fillBaseFields(Utilisateur user, UtilisateurDTO dto) {
        user.setNom(dto.getNom());
        user.setPrenom(dto.getPrenom());
        user.setMatricule(dto.getMatricule());
        user.setMotDePasse(dto.getMotDePasse());
        user.setTelephone(dto.getTelephone());
        user.setEmail(dto.getEmail());
    }

    private UtilisateurDTO mapEntityToDto(Utilisateur user) {
        UtilisateurDTO dto;
        if (user instanceof Technicien t) {
            TechnicienDTO techDto = new TechnicienDTO();
            techDto.setNiveau(t.getNiveau());
            dto = techDto;
            dto.setType(UtilisateurType.TECHNICIEN);
        } else if (user instanceof Administrateur) {
            dto = new UtilisateurDTO();
            dto.setType(UtilisateurType.ADMINISTRATEUR);
        } else if (user instanceof Responsable) {
            dto = new UtilisateurDTO();
            dto.setType(UtilisateurType.RESPONSABLE);
        } else {
            dto = new UtilisateurDTO();
            dto.setType(UtilisateurType.DEMANDEUR);
        }

        dto.setId(user.getId());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setMatricule(user.getMatricule());
        dto.setTelephone(user.getTelephone());
        dto.setEmail(user.getEmail());
        return dto;
    }
    private DemandeurDTO mapToDemandeurDTO(Demandeur d) {
        DemandeurDTO dto = new DemandeurDTO();
        dto.setId(d.getId());
        dto.setDateDeCreation(d.getDateDeCreation());
        dto.setNom(d.getNom());
        dto.setPrenom(d.getPrenom());
        dto.setMatricule(d.getMatricule());
        dto.setTelephone(d.getTelephone());
        dto.setEmail(d.getEmail());
        dto.setStatu(d.getStatu());
        dto.setDepartement(d.getDepartement());
        dto.setType(UtilisateurType.DEMANDEUR);
        return dto;
    }

    private ResponsableDTO mapToResponsableDTO(Responsable r) {
        ResponsableDTO dto = new ResponsableDTO();
        dto.setId(r.getId());
        dto.setDateDeCreation(r.getDateDeCreation());
        dto.setNom(r.getNom());
        dto.setPrenom(r.getPrenom());
        dto.setMatricule(r.getMatricule());
        dto.setTelephone(r.getTelephone());
        dto.setEmail(r.getEmail());
        dto.setStatu(r.getStatu());
        dto.setTypeService(r.getTypeService());
        dto.setType(UtilisateurType.RESPONSABLE);
        return dto;
    }

    private TechnicienDTO mapToTechnicienDTO(Technicien t) {
        TechnicienDTO dto = new TechnicienDTO();
        dto.setId(t.getId());
        dto.setDateDeCreation(t.getDateDeCreation());
        dto.setNom(t.getNom());
        dto.setPrenom(t.getPrenom());
        dto.setStatu(t.getStatu());
        dto.setMatricule(t.getMatricule());
        dto.setTelephone(t.getTelephone());
        dto.setEmail(t.getEmail());
        dto.setNiveau(t.getNiveau());
        dto.setType(UtilisateurType.TECHNICIEN);
        return dto;
    }
    @Override
    @Transactional
    public void changerStatut(Long idUtilisateur) {
        Utilisateur utilisateur = utilisateurRepository.findById(idUtilisateur)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + idUtilisateur));

        String statutActuel = utilisateur.getStatu();
        if ("ACTIF".equalsIgnoreCase(statutActuel)) {
            utilisateur.setStatu("INACTIF");
        } else {
            utilisateur.setStatu("ACTIF");
        }

        utilisateurRepository.save(utilisateur);
    }
    @Override
    public DemandeurDTO getDemandeurById(Long id) {
        Demandeur demandeur = demandeurRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Demandeur non trouvé avec l'ID : " + id));
        return mapToDemandeurDTO(demandeur);
    }

    @Override
    public ResponsableDTO getResponsableById(Long id) {
        Responsable responsable = responsableRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Responsable non trouvé avec l'ID : " + id));
        return mapToResponsableDTO(responsable);
    }

    @Override
    public TechnicienDTO getTechnicienById(Long id) {
        Technicien technicien = technicienRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Technicien non trouvé avec l'ID : " + id));
        return mapToTechnicienDTO(technicien);
    }

    public List<TechnicienDTO> getTechniciensByNiveau(Integer niveau) {
        List<Technicien> techniciens = technicienRepository.findByNiveau(niveau);
        return techniciens.stream()
        		.map(this::mapToTechnicienDTO).toList();
    }

}
