package com.bml.appAcademia.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(c -> c.disable())//Desabilita a proteção contra ataques CSRF. O próprio Token já protege contra isso
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Configura a autenticação para ser StateLess
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login","h2-console/**").permitAll() //Permito requisições para essas URLs
                .anyRequest().authenticated() //Qualquer outra requisição só permito se estiver autenticado
            )
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) //Informo que meu filtro precisa ser chamado antes do Filter do Spring
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
