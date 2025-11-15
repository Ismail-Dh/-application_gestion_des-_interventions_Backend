package ma.ocp.log_service.services_impl;

import ma.ocp.log_service.dto.LogAppDTO;
import ma.ocp.log_service.entities.LogApp;
import ma.ocp.log_service.repositories.LogAppRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogAppServiceImplTest {

    @Mock
    private LogAppRepository logAppRepository;

    @InjectMocks
    private LogAppServiceImpl logAppService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEnregistrerLog() {
        // Arrange
        LogAppDTO dto = new LogAppDTO();
        dto.setSourceService("UserService");
        dto.setAction("CREATE_USER");
        dto.setMatricule("M123");
        dto.setMessage("User created successfully");
        dto.setDateAction(new Date());

        LogApp logApp = LogApp.builder()
                .sourceService(dto.getSourceService())
                .action(dto.getAction())
                .matricule(dto.getMatricule())
                .message(dto.getMessage())
                .dateAction(dto.getDateAction())
                .build();

        when(logAppRepository.save(any(LogApp.class))).thenReturn(logApp);

        // Act
        LogApp savedLog = logAppService.enregistrerLog(dto);

        // Assert
        assertNotNull(savedLog);
        assertEquals(dto.getSourceService(), savedLog.getSourceService());
        assertEquals(dto.getAction(), savedLog.getAction());
        assertEquals(dto.getMatricule(), savedLog.getMatricule());
        assertEquals(dto.getMessage(), savedLog.getMessage());
        assertEquals(dto.getDateAction(), savedLog.getDateAction());
        verify(logAppRepository, times(1)).save(any(LogApp.class));
    }

    @Test
    void testGetAllLogs() {
        // Arrange
        LogApp log1 = LogApp.builder().sourceService("UserService").action("CREATE_USER").build();
        LogApp log2 = LogApp.builder().sourceService("OrderService").action("CREATE_ORDER").build();

        when(logAppRepository.findAll()).thenReturn(Arrays.asList(log1, log2));

        // Act
        List<LogApp> logs = logAppService.getAllLogs();

        // Assert
        assertNotNull(logs);
        assertEquals(2, logs.size());
        verify(logAppRepository, times(1)).findAll();
    }
}
