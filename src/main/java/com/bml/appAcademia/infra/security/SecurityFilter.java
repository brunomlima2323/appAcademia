package com.bml.appAcademia.infra.security;

import com.bml.appAcademia.domain.usuario.UsuarioRepository;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String tokenJWT = recuperarToken(request);

            if (tokenJWT != null && !tokenJWT.isBlank()) {
                String subject = tokenService.getSubject(tokenJWT);
                var usuario = usuarioRepository.findByLogin(subject);

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(
                                usuario, null, usuario.getAuthorities()
                        );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }

        } catch (Exception e) {
            // NUNCA bloqueie aqui
            // Apenas logue
            System.out.println("Token inválido ou ausente: " + e.getMessage());
        }

        // SEMPRE continue o filtro
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String authorizatioHeader = request.getHeader("Authorization");
        if(authorizatioHeader != null){
            return authorizatioHeader.replace("Bearer ", "");
        }
        return null;
    }
}
