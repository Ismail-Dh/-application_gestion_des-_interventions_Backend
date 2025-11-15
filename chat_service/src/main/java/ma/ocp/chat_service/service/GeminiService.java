package ma.ocp.chat_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GeminiService {

    private final WebClient webClient;
    private final String apiKey;

    public GeminiService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://generativelanguage.googleapis.com").build();
        this.apiKey = System.getenv("GEMINI_API_KEY"); // Configure GEMINI_API_KEY
        if (this.apiKey == null) {
            throw new IllegalStateException("⚠️ Clé API manquante. Configure GEMINI_API_KEY.");
        }
    }

    public String askGemini(String prompt) {
        try {
            String body = """
            {
              "contents": [
                { "parts": [ { "text": "%s" } ] }
              ]
            }
            """.formatted(prompt.replace("\"", "\\\"").replace("\n", "\\n"));

            JsonNode response = webClient.post()
                    .uri("/v1beta/models/gemini-2.0-flash:generateContent")
                    .header("X-goog-api-key", apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            return response.path("candidates").get(0)
                           .path("content").path("parts").get(0)
                           .path("text").asText("Réponse introuvable");

        } catch (Exception e) {
            return "Erreur lors de l'appel à Gemini : " + e.getMessage();
        }
    }
}



