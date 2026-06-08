package com.petcare.sistema_petshop.security;

import com.petcare.sistema_petshop.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Avisa ao Spring para gerenciar essa classe como um componente genérico
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Extrai o token que veio na requisição HTTP
        String token = recuperarToken(request);

        // 2. Se o token existir, faz a validação dele
        if (token != null) {
            String login = tokenService.validarToken(token); // O TokenService descriptografa e devolve o e-mail/login

            if (login != null) {
                // 3. Busca o usuário no banco de dados para garantir que ele ainda existe e está ativo
                UserDetails usuario = usuarioRepository.findByLogin(login);

                // 4. Cria o "objeto de autenticação" que o Spring Security exige
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

                // 5. Autentica oficialmente o usuário no motor interno do Spring Security
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 6. Envia a requisição adiante (para o próximo filtro ou para a Controller)
        filterChain.doFilter(request, response);
    }


    private String recuperarToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        // O padrão de mercado diz que o token vem precedido da palavra "Bearer " (ex: Bearer eyJhbG...)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        // Remove a palavra "Bearer " e devolve apenas o texto puro do Token JWT
        return authHeader.replace("Bearer ", "");
    }
}