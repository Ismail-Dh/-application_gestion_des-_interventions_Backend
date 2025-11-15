package ma.ocp.log_service.services_impl;

import ma.ocp.log_service.dto.LogAppDTO;
import ma.ocp.log_service.entities.LogApp;
import ma.ocp.log_service.repositories.LogAppRepository;
import ma.ocp.log_service.services.LogAppService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogAppServiceImpl implements LogAppService {

    private final LogAppRepository repo;

    public LogAppServiceImpl(LogAppRepository repo) {
        this.repo = repo;
    }

    @Override
    public LogApp enregistrerLog(LogAppDTO dto) {
        LogApp log = mapDtoToEntity(dto);
        return repo.save(log);
    }

    @Override
    public List<LogApp> getAllLogs() {
        return repo.findAll();
    }

    private LogApp mapDtoToEntity(LogAppDTO dto) {
        return LogApp.builder()
                .sourceService(dto.getSourceService())
                .action(dto.getAction())
                .matricule(dto.getMatricule())
                .message(dto.getMessage())
                .dateAction(dto.getDateAction() != null ? dto.getDateAction() : new Date())
                .build();
    }
}
