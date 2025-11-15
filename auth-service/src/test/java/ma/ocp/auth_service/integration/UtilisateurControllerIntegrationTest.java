package ma.ocp.auth_service.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import ma.ocp.auth_service.dto.UtilisateurDTO;
import ma.ocp.auth_service.enums.UtilisateurType;
import ma.ocp.auth_service.repositories.UtilisateurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
class UtilisateurControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() throws Exception {
        utilisateurRepository.deleteAll();
        // Créer un utilisateur ADMIN pour les tests
        UtilisateurDTO adminUser = new UtilisateurDTO();
        adminUser.setNom("Admin");
        adminUser.setPrenom("Test");
        adminUser.setMatricule("ADMIN001");
        adminUser.setTelephone("0600000000");
        adminUser.setEmail("admin@ocp.ma");
        adminUser.setMotDePasse("adminpass"); // Mot de passe en clair, sera encodé par le service
        adminUser.setType(UtilisateurType.ADMINISTRATEUR);

        mockMvc.perform(post("/api/utilisateurs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminUser)))
                .andExpect(status().isOk());
    }

    @Test
    
    void testCreateAndGetUtilisateur() throws Exception {
        // Création d'un utilisateur
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setNom("Test");
        utilisateurDTO.setPrenom("Integration");
        utilisateurDTO.setMatricule("123");
        utilisateurDTO.setTelephone("0612345678");
        utilisateurDTO.setEmail("test@ocp.ma");
        utilisateurDTO.setMotDePasse("pass");
        utilisateurDTO.setType(UtilisateurType.ADMINISTRATEUR);

        String jsonRequest = objectMapper.writeValueAsString(utilisateurDTO);

        String adminToken = obtainJwtToken("ADMIN001", "adminpass");

        // POST /api/utilisateurs
        String response = mockMvc.perform(post("/api/utilisateurs")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nom").value("Test"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UtilisateurDTO savedUser = objectMapper.readValue(response, UtilisateurDTO.class);

        // Vérifie en base
        assertThat(utilisateurRepository.findById(savedUser.getId())).isPresent();

        // GET /api/utilisateurs/{id}
        mockMvc.perform(get("/api/utilisateurs/" + savedUser.getId())
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Test"))
                .andExpect(jsonPath("$.prenom").value("Integration"));
    }

    @Test
    void testGetAllUtilisateurs() throws Exception {
        // Création en base
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setNom("User");
        utilisateurDTO.setPrenom("All");
        utilisateurDTO.setMatricule("111");
        utilisateurDTO.setEmail("all@ocp.ma");
        utilisateurDTO.setTelephone("0612345678");
        utilisateurDTO.setMotDePasse("pass");
        utilisateurDTO.setType(UtilisateurType.ADMINISTRATEUR);

        String jsonRequest = objectMapper.writeValueAsString(utilisateurDTO);
        mockMvc.perform(post("/api/utilisateurs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)).andExpect(status().isOk());

        String adminToken = obtainJwtToken("ADMIN001", "adminpass");

        mockMvc.perform(get("/api/utilisateurs")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(greaterThanOrEqualTo(2)));
    }
    
    @Test
    void testModifierUtilisateur() throws Exception {
        // Création initiale d’un utilisateur
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setNom("Original");
        utilisateurDTO.setPrenom("User");
        utilisateurDTO.setMatricule("333");
        utilisateurDTO.setEmail("original@ocp.ma");
        utilisateurDTO.setTelephone("0612345678");
        utilisateurDTO.setMotDePasse("pass");
        utilisateurDTO.setType(UtilisateurType.ADMINISTRATEUR);

        String adminToken = obtainJwtToken("ADMIN001", "adminpass");

        String jsonCreate = objectMapper.writeValueAsString(utilisateurDTO);
        String responseCreate = mockMvc.perform(post("/api/utilisateurs")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCreate))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        UtilisateurDTO savedUser = objectMapper.readValue(responseCreate, UtilisateurDTO.class);

        // Préparation des données modifiées
     // Préparation des données modifiées
        savedUser.setNom("Modifié");
        savedUser.setPrenom("UserMod");
        savedUser.setMatricule("333"); // obligatoire
        savedUser.setEmail("modifie@ocp.ma"); // obligatoire
        savedUser.setTelephone("0612345678"); // obligatoire
        savedUser.setMotDePasse("pass"); // obligatoire
        

        String jsonUpdate = objectMapper.writeValueAsString(savedUser);

        // Appel PUT pour modifier l’utilisateur
        mockMvc.perform(put("/api/utilisateurs/" + savedUser.getId())
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUpdate))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nom").value("Modifié"))
            .andExpect(jsonPath("$.prenom").value("UserMod"));

        // Vérifier en base que la modification est effective
        var utilisateurEnBase = utilisateurRepository.findById(savedUser.getId()).orElseThrow();
        assertThat(utilisateurEnBase.getNom()).isEqualTo("Modifié");
        assertThat(utilisateurEnBase.getPrenom()).isEqualTo("UserMod");
    }


    @Test
    void testDeleteUtilisateur() throws Exception {
        // Insère un utilisateur
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setNom("Delete");
        utilisateurDTO.setPrenom("Me");
        utilisateurDTO.setMatricule("222");
        utilisateurDTO.setEmail("delete@ocp.ma");
        utilisateurDTO.setTelephone("0612345678");
        utilisateurDTO.setMotDePasse("pass");
        utilisateurDTO.setType(UtilisateurType.ADMINISTRATEUR);

        String adminToken = obtainJwtToken("ADMIN001", "adminpass");

        String jsonRequest = objectMapper.writeValueAsString(utilisateurDTO);
        String response = mockMvc.perform(post("/api/utilisateurs")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)).andReturn().getResponse().getContentAsString();

        UtilisateurDTO savedUser = objectMapper.readValue(response, UtilisateurDTO.class);

        // DELETE
        mockMvc.perform(delete("/api/utilisateurs/" + savedUser.getId())
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNoContent());

        assertThat(utilisateurRepository.findById(savedUser.getId())).isNotPresent();
    }






    private String obtainJwtToken(String matricule, String password) throws Exception {
        String authRequestJson = String.format("{\"matricule\":\"%s\", \"motDePasse\":\"%s\"}", matricule, password);

        String response = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequestJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extraire uniquement la valeur du token
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        String token = jsonNode.get("token").asText();

        return token;
    }

    

}
