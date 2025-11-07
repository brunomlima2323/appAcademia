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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenJWT = recuperarToken(request); //Recupera o token JWT do cabeçalho da requisição

        if (tokenJWT != null){
            String subject = tokenService.getSubject(tokenJWT); //Valida o token e extrai o "subject" (usuário ou e-mail) contido no JWT.
            var usuario = usuarioRepository.findByLogin(subject); //Busca o usuário no banco de dados usando o login (subject) extraído do token.
            Authentication authentication = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities()); //Cria o objeto de autenticação do Spring (Authentication) com o usuário autenticado e suas permissões.
            SecurityContextHolder.getContext().setAuthentication(authentication); //Informa ao Spring Security que o usuário está autenticado, armazenando os dados no contexto de segurança.
            System.out.println("LOGADO NA REQUISISAO");
        }

        filterChain.doFilter(request,response); //Continua a execução do filtro, permitindo que a requisição siga adiante.
    }

    private String recuperarToken(HttpServletRequest request) {
        String authorizatioHeader = request.getHeader("Authorization");
        if(authorizatioHeader != null){
            return authorizatioHeader.replace("Bearer ", "");
        }
        return null;
    }
}
