

package ma.ocp.equipement_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"ma.ocp.equipement_service","ma.ocp.equipment_service","ma.ocp.shared"})

@EnableJpaRepositories(basePackages = "ma.ocp.equipment_service.repositories")
@EntityScan("ma.ocp.equipment_service.entities")
public class EquipementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EquipementServiceApplication.class, args);
	}

}
