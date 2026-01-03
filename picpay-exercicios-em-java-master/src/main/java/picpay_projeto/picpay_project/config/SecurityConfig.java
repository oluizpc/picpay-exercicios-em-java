package picpay_projeto.picpay_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // desabilita CSRF (ok para dev)
            .csrf(csrf -> csrf.disable())

            // libera todas as rotas
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())

            // 👇 ESSENCIAL para o H2 Console
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}
