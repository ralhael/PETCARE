package com.petcare.sistema_petshop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity      // dizem para ler a classe na inicializacao antes de qualquer coisa chegar em alguma controller
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(java.util.List.of("http://localhost:5173", "http://localhost:5174"));
                    corsConfiguration.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
                    corsConfiguration.setAllowedHeaders(java.util.List.of("*"));
                    return corsConfiguration;
                }))
                .csrf(csrf -> csrf.disable()) // desabilita a segurança de roubo de cookies
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        //  Libera absolutamente TUDO no sistema para testes sem barreiras
                        .anyRequest().permitAll()
                )
                // Desligamos o filtro comentando a linha dele para ele não travar nada
                // .addFilterBefore(securityFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){       // decodificador de senhar ( ele transforma a senha em um bagulho nada haver dficultando o acesso de pegar a senha real)
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder, AutenticacaoService autenticacaoService) throws Exception {
        org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder.class);

        //  o serviço de busca (AutenticacaoService) com o codificador (BCrypt)
        authenticationManagerBuilder
                .userDetailsService(autenticacaoService)
                .passwordEncoder(passwordEncoder);

        return authenticationManagerBuilder.build();
    }

}
