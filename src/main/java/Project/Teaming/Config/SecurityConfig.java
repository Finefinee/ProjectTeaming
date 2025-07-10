package Project.Teaming.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // (테스트용이면 csrf 꺼두고)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/invite").authenticated()
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
