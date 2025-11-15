package ma.ocp.chat_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SolutionClientService {

    private final WebClient webClient;

    public SolutionClientService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8084/api/interventions").build();
    }

    public List<String> getAnciennesSolutions(String probleme) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/solutions")
                        .queryParam("probleme", probleme)
                        .build())
                .retrieve()
                .bodyToFlux(String.class)
                .collectList()
                .block();
    }
    
    

}
