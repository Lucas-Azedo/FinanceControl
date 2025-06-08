package com.example.FinanceControl.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.FinanceControl.model.User;
import com.example.FinanceControl.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String userIdStr;
        UUID userId;

        if (!isBearerToken(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring("Bearer ".length());

        try {
            userIdStr = tokenService.validateTokenAndGetUserId(token);
        } catch (TokenExpiredException e) {
            sendErrorResponse(response, "Token expired");
            return;
        } catch (JWTVerificationException e) {
            sendErrorResponse(response, "Invalid token");
            return;
        }

        if (userIdStr == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            userId = UUID.fromString(userIdStr);
        } catch (IllegalArgumentException e) {
            // userIdStr inválido
            filterChain.doFilter(request, response);
            return;
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            filterChain.doFilter(request, response);
            return;
        }

        authenticateUser(user, request);
        filterChain.doFilter(request, response);
    }

    private boolean isBearerToken(String header) {
        return header != null && header.startsWith("Bearer ");
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(message);
    }

    private void authenticateUser(User user, HttpServletRequest request) {
        UserAuthenticated userAuthenticated = new UserAuthenticated(user);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userAuthenticated,
                null,
                userAuthenticated.getAuthorities());

        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
