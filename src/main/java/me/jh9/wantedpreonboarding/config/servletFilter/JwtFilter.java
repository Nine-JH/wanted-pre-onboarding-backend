package me.jh9.wantedpreonboarding.config.servletFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import me.jh9.wantedpreonboarding.common.jwt.application.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain) throws IOException, ServletException {

        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!isJwtExist(accessToken)) {
            chain.doFilter(request, response);
            return;
        }

        String memberId = jwtService.verify(accessToken).getSubject();
        request.setAttribute("memberId", Long.valueOf(memberId));

        SecurityContextHolder.getContext()
            .setAuthentication(UsernamePasswordAuthenticationToken.authenticated(memberId, null,
                Collections.emptySet()));

        chain.doFilter(request, response);
    }

    private boolean isJwtExist(String token) {
        return token != null && !token.isBlank();
    }
}
