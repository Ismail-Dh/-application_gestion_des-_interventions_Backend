package ma.ocp.log_service.services;

import ma.ocp.log_service.dto.LogAppDTO;
import ma.ocp.log_service.entities.LogApp;

import java.util.List;

public interface LogAppService {
    LogApp enregistrerLog(LogAppDTO dto);   
    List<LogApp> getAllLogs();
}
