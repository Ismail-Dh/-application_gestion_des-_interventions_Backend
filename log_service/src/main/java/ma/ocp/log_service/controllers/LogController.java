package ma.ocp.log_service.controllers;

import ma.ocp.log_service.dto.LogAppDTO;
import ma.ocp.log_service.entities.LogApp;
import ma.ocp.log_service.services.LogAppService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/logs")
public class LogController {

    private final LogAppService logAppService;

    public LogController(LogAppService logAppService) {
        this.logAppService = logAppService;
    }

    @PostMapping
    public LogApp recevoirLog(@RequestBody LogAppDTO logDto) {
        return logAppService.enregistrerLog(logDto);
    }


    @GetMapping
    public List<LogApp> afficherLogs() {
        return logAppService.getAllLogs();
    }
    
   

 
}
