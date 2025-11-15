package ma.ocp.auth_service.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("DEMANDEUR")
@Data
public class Demandeur extends Utilisateur {
    private String departement;
}
