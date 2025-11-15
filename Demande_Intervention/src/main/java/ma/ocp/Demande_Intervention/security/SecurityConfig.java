package ma.ocp.Demande_Intervention.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ma.ocp.shared.security.JwtFilter;

import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(request -> {
                var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                corsConfig.setAllowedOrigins(java.util.List.of("http://localhost:4200")); // autoriser l'origine Angular
                corsConfig.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                corsConfig.setAllowedHeaders(java.util.List.of("*"));
                corsConfig.setAllowCredentials(true);
                corsConfig.setMaxAge(3600L);
                return corsConfig;
            }))
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers("/api/interventions/solutions/**").permitAll()
                .requestMatchers("/api/Demandes/**").hasAnyRole("ADMINISTRATEUR","DEMANDEUR","RESPONSABLE", "TECHNICIEN")
                .requestMatchers("/api/interventions/**").hasAnyRole("ADMINISTRATEUR", "DEMANDEUR","TECHNICIEN","RESPONSABLE")
                .requestMatchers("/api/planifications/**").hasAnyRole("ADMINISTRATEUR", "TECHNICIEN")
                
                .anyRequest().permitAll()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
