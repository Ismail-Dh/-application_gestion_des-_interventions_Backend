package ma.ocp.Demande_Intervention;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"ma.ocp.Demande_Intervention","ma.ocp.shared"})
@EnableScheduling
public class DemandeInterventionApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemandeInterventionApplication.class, args);
	}

}
