package br.com.erudio.security.jwt;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class JwtTokenFilter extends GenericFilterBean {

    @Autowired
    private JwtTokenProvider tokenProvider;

    public JwtTokenFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();

        // Log para monitorar os endpoints interceptados
        logger.info("Interceptando requisição para: " + path);

        // Ignorar endpoints públicos
        if (path.startsWith("/auth/signin") || path.startsWith("/auth/refresh")) {
            logger.info("Ignorando autenticação para o endpoint público: " + path);
            chain.doFilter(request, response);
            return;
        }

        try {
            // Obter o token do cabeçalho Authorization
            String token = tokenProvider.resolveToken(httpRequest);

            if (token != null && tokenProvider.validateToken(token)) {
                // Obter a autenticação com base no token
                Authentication auth = tokenProvider.getAuthentication(token);

                if (auth != null) {
                    // Configurar o contexto de segurança do Spring
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    logger.info("Usuário autenticado com sucesso para o endpoint: " + path);
                }
            }
        } catch (Exception e) {
            // Logar exceções sem interromper o fluxo
            logger.warn("Erro ao autenticar a requisição para "+ path +": " + e.getMessage());
        }

        // Continuar o processamento da requisição
        chain.doFilter(request, response);
    }
}