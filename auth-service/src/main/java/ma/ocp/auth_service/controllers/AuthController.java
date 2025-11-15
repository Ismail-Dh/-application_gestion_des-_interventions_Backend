package ma.ocp.auth_service.controllers;

import ma.ocp.auth_service.dto.LoginRequestDTO;
import ma.ocp.auth_service.dto.LoginResponseDTO;
import ma.ocp.auth_service.entities.Utilisateur;
import ma.ocp.auth_service.exceptions.MotDePasseIncorrectException;
import ma.ocp.auth_service.exceptions.UtilisateurIntrouvableException;
import ma.ocp.auth_service.messaging.LogSender;
import ma.ocp.auth_service.repositories.UtilisateurRepository;
import ma.ocp.log_service.dto.LogAppDTO;
import ma.ocp.shared.security.JwtUtil;

import java.util.Date;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final LogSender logSender;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginDto) {
        Utilisateur utilisateur = utilisateurRepository.findByMatricule(loginDto.getMatricule())
                .orElseThrow(() -> new UtilisateurIntrouvableException("Utilisateur introuvable"));

        if (!passwordEncoder.matches(loginDto.getMotDePasse(), utilisateur.getMotDePasse())) {
            throw new MotDePasseIncorrectException("Mot de passe incorrect");
        }

        String type;
        if (utilisateur instanceof ma.ocp.auth_service.entities.Administrateur) {
            type = "ADMINISTRATEUR";
        } else if (utilisateur instanceof ma.ocp.auth_service.entities.Responsable) {
            type = "RESPONSABLE";
        } else if (utilisateur instanceof ma.ocp.auth_service.entities.Technicien) {
            type = "TECHNICIEN";
        } else {
            type = "DEMANDEUR";
        }

        
        String token = jwtUtil.generateToken(utilisateur.getMatricule(), type);

        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(token);
        response.setNom(utilisateur.getNom());
        response.setPrenom(utilisateur.getPrenom());
        response.setMatricule(utilisateur.getMatricule());
        response.setType(type);
        response.setId(utilisateur.getId());
        
        LogAppDTO log = new LogAppDTO();
        log.setSourceService("auth_service");
        log.setAction("CONNEXION");
        log.setMatricule(utilisateur.getMatricule());
        log.setMessage("Connexion réussie");
        log.setDateAction(new Date());
        logSender.envoyerLog(log);

        return response;
    }

}
