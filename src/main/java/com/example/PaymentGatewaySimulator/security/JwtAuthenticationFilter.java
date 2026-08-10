package com.example.PaymentGatewaySimulator.security;

import com.example.PaymentGatewaySimulator.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Read Authorization header
        String authHeader =
                request.getHeader("Authorization");

        // 2. No Bearer token
        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extract JWT
        String token =
                authHeader.substring(7);

        try {

            // 4. Extract username
            String username =
                    jwtService.extractUsername(token);

            // 5. Check whether user is already authenticated
            if (username != null &&
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication() == null) {

                // 6. Load user
                UserDetails userDetails =
                        userDetailsService
                                .loadUserByUsername(username);

                // 7. Validate JWT
                if (jwtService.isTokenValid(
                        token,
                        userDetails.getUsername())) {

                    // 8. Create authenticated user
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    // 9. Store authentication
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);
                }
            }

        } catch (Exception exception) {

            // Invalid JWT → continue without authentication
            SecurityContextHolder.clearContext();
        }

        // 10. Continue request
        filterChain.doFilter(request, response);
    }
}