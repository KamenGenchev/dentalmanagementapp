package com.dentalmanagementapp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;

    @Autowired
    public JwtRequestFilter(@NotNull JwtUtil jwtUtil, @NotNull CustomUserDetailService customUserDetailService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailService = customUserDetailService;
    }

    @Transactional
    @Override
    protected void doFilterInternal(final @NotNull HttpServletRequest request, final @NotNull HttpServletResponse response, final @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            validateRequestToken(request);
        } catch (Exception ex) {
            //TODO
        }
        filterChain.doFilter(request, response);
    }

    @Transactional
    protected void validateRequestToken(HttpServletRequest request) {
        String authToken = jwtUtil.generateToken(String.valueOf(request));
        if (authToken != null) {
            String username = jwtUtil.getUsername(authToken);
            if (username != null) {
                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
                authenticationProcedure(authToken, userDetails, request);
            }
        }
    }

    private void authenticationProcedure(String authToken, UserDetails userDetails, HttpServletRequest request) {
        if (jwtUtil.isTokenValid(authToken, userDetails)) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
}