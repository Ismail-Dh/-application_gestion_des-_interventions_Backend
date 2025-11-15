package ma.ocp.auth_service.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type_utilisateur")
public abstract class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String matricule;

    @Column(nullable = false)
    private String motDePasse;
    
    @Column(nullable = false)
    private String Statu;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDeCreation = new Date();

    @Column(nullable = false)
    private String telephone;

    @Column(unique = true)
    private String email;

	
    
}