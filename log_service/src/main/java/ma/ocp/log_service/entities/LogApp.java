package ma.ocp.log_service.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogApp {
    @Id
    private String id;
    private String sourceService;
    private String action;
    private String matricule;
    private String message;
    private Date dateAction;
}
