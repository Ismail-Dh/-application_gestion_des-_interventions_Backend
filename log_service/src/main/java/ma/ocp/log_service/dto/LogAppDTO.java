package ma.ocp.log_service.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LogAppDTO {
    private String sourceService;
    private String action;
    private String matricule;
    private String message;
    private Date dateAction; 
}
