package ma.ocp.auth_service.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
@Data
@Entity
@DiscriminatorValue("TECHNICIEN")
public class Technicien extends Utilisateur {
    private Integer niveau;

	
    
    
}
