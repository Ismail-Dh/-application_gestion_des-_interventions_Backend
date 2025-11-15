package ma.ocp.chat_service.controleur;

import ma.ocp.chat_service.service.ChatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:4200")
public class ChatController {

    @Autowired
    private ChatBotService chatBotService;

    @PostMapping
    public ResponseEntity<Map<String, String>> ask(@RequestBody Map<String, String> req) {
        String question = req.get("question");
        String answer = chatBotService.ask(question);
        return ResponseEntity.ok(Map.of("answer", answer));
    }
}
