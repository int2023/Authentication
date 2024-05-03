package com.example.demo.secure;

import com.example.demo.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private JWTFilter filter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration conf) throws Exception {
        return conf.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/authenticate").permitAll())
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/authenticateTest").permitAll())
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/reg").permitAll())
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN"))
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN"))
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN"))
                .sessionManagement((auth) -> auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
