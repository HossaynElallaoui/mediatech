package com.estc.mediatech.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtGenerator tokenGenerator;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Skip JWT authentication for public endpoints
        if (path.startsWith("/api/auth/")) {
            System.out.println("Skipping JWT for public endpoint: " + path);
            filterChain.doFilter(request, response);
            return;
        }

        String token = getJWTFromRequest(request);

        if (StringUtils.hasText(token)) {
            System.out.println("JWT token found in request for path: " + path);
            try {
                if (tokenGenerator.validateToken(token)) {
                    String username = tokenGenerator.getUsernameFromJWT(token);
                    System.out.println("Valid JWT token for user: " + username);

                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    System.out.println("Authentication set successfully for user: " + username);
                } else {
                    System.out.println("JWT token validation failed");
                }
            } catch (Exception e) {
                System.out.println("Error processing JWT token: " + e.getMessage());
            }
        } else {
            System.out.println("No JWT token found in request for path: " + path);
        }

        filterChain.doFilter(request, response);
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
