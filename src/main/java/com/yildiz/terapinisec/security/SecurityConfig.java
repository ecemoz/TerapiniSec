package com.yildiz.terapinisec.config;

import com.yildiz.terapinisec.service.CustomUserDetailsService;
import com.yildiz.terapinisec.security.JwtAuthenticationFilter;
import com.yildiz.terapinisec.util.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity // @PreAuthorize, @Secured vb. annotasyonları kullanabilmek için
public class SecurityConfig {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authProvider);
    }

    /**
     * Esas SecurityFilterChain burada tanımlanır.
     * Hangi endpoint'lere kim erişebilecek, nasıl bir yol izlenecek vs.
     */
    @Bean
    public SecurityFilterChain filterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {

        // CSRF kapat (JWT ile genellikle kapatılır)
        http.csrf(csrf -> csrf.disable());

        // Session yok, JWT var:
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Yetkilendirme kuralları (ANT Matcher bazlı).
        http.authorizeHttpRequests(auth -> auth
                // Kayıt (register) veya login olan endpoint herkese açık:
                .requestMatchers("/users/authenticate").permitAll()
                .requestMatchers("/users").hasRole("ADMIN") // Admin rolüne özel örnek
                // Geri kalan her endpoint en az "USER" rolü isteyebilir
                .anyRequest().authenticated()
        );

        // Exception handling (401, 403 vs.)
        http.exceptionHandling(Customizer.withDefaults());

        // Kendi yazdığımız JWT filtreyi UsernamePasswordAuthenticationFilter'dan önce ekliyoruz:
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
