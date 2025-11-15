package ma.ocp.auth_service.services_impl;

import ma.ocp.auth_service.dto.*;
import ma.ocp.auth_service.entities.*;
import ma.ocp.auth_service.enums.UtilisateurType;
import ma.ocp.auth_service.repositories.UtilisateurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UtilisateurServiceImplTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UtilisateurServiceImpl utilisateurService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Test creerUtilisateur
    @Test
    void testCreerUtilisateur() {
        UtilisateurDTO dto = new UtilisateurDTO();
        dto.setNom("Admin");
        dto.setPrenom("Test");
        dto.setMotDePasse("1234");
        dto.setType(UtilisateurType.ADMINISTRATEUR);

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPass");
        when(utilisateurRepository.save(any(Utilisateur.class))).thenAnswer(i -> i.getArgument(0));

        UtilisateurDTO result = utilisateurService.creerUtilisateur(dto);

        assertNotNull(result);
        assertEquals(UtilisateurType.ADMINISTRATEUR, result.getType());
        verify(utilisateurRepository, times(1)).save(any(Utilisateur.class));
        verify(passwordEncoder).encode("1234");
    }

    // Test modifierUtilisateur pour Responsable
    @Test
    void testModifierUtilisateur_Responsable() {
        Responsable responsable = new Responsable();
        responsable.setId(1L);

        ResponsableDTO dto = new ResponsableDTO();
        dto.setNom("New Name");
        dto.setPrenom("New Prenom");
        dto.setEmail("new@email.com");
        dto.setTelephone("0600000000");
        dto.setMotDePasse("newpass");
        dto.setType(UtilisateurType.RESPONSABLE);
        dto.setTypeService("IT");

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(responsable));
        when(utilisateurRepository.save(any(Utilisateur.class))).thenAnswer(i -> i.getArgument(0));

        UtilisateurDTO updated = utilisateurService.modifierUtilisateur(1L, dto);

        assertNotNull(updated);
        verify(utilisateurRepository).save(any(Utilisateur.class));
    }

    // Test modifierUtilisateur pour Technicien
    @Test
    void testModifierUtilisateur_Technicien() {
        Technicien technicien = new Technicien();
        technicien.setId(2L);

        TechnicienDTO dto = new TechnicienDTO();
        dto.setNom("Tech Name");
        dto.setPrenom("Tech Prenom");
        dto.setEmail("tech@mail.com");
        dto.setTelephone("0666111222");
        dto.setMotDePasse("techpass");
        dto.setType(UtilisateurType.TECHNICIEN);
        dto.setNiveau(1);

        when(utilisateurRepository.findById(2L)).thenReturn(Optional.of(technicien));
        when(utilisateurRepository.save(any(Utilisateur.class))).thenAnswer(i -> i.getArgument(0));

        UtilisateurDTO updated = utilisateurService.modifierUtilisateur(2L, dto);

        assertNotNull(updated);
        verify(utilisateurRepository).save(any(Utilisateur.class));
    }

    // Test getUtilisateurById
    @Test
    void testGetUtilisateurById() {
        Administrateur admin = new Administrateur();
        admin.setId(1L);
        admin.setNom("Admin");

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(admin));

        UtilisateurDTO dto = utilisateurService.getUtilisateurById(1L);

        assertNotNull(dto);
        assertEquals("Admin", dto.getNom());
    }

    // Test getAllUtilisateurs
    @Test
    void testGetAllUtilisateurs() {
        Administrateur admin = new Administrateur();
        admin.setId(1L);
        admin.setNom("Admin");

        when(utilisateurRepository.findAll()).thenReturn(Arrays.asList(admin));

        List<UtilisateurDTO> result = utilisateurService.getAllUtilisateurs();

        assertFalse(result.isEmpty());
        assertEquals("Admin", result.get(0).getNom());
    }

    // Test supprimerUtilisateur
    @Test
    void testSupprimerUtilisateur() {
        utilisateurService.supprimerUtilisateur(1L);
        verify(utilisateurRepository).deleteById(1L);
    }
}
