package com.dentalmanagementapp.security;

import com.dentalmanagementapp.util.SecurityUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final SecurityUtil securityUtil;

    @Autowired
    public JwtRequestFilter(JwtUtil jwtUtil, CustomUserDetailService customUserDetailService, SecurityUtil securityUtil) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailService = customUserDetailService;
        this.securityUtil = securityUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            validateRequestToken(request);
            filterChain.doFilter(request, response);
        } finally {
            UserContext.clear();
        }
    }

    protected void validateRequestToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        System.out.println("Auth header: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String authToken = authHeader.substring(7);
            System.out.println("Auth token: " + authToken);
            String username = jwtUtil.getUsername(authToken);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
                authenticationProcedure(authToken, userDetails, request);
                UserContext.setCurrentUser(securityUtil.getCurrentUserContext());
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