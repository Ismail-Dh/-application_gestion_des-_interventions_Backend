package ma.ocp.auth_service.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("RESPONSABLE")
@Data
public class Responsable extends Utilisateur {
    private String typeService;
}
