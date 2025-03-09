package com.yildiz.terapinisec.util;

import com.yildiz.terapinisec.dto.UserResponseDto;
import com.yildiz.terapinisec.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    JwtUtil jwtUtil;

    @Lazy
    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        logger.info("JwtRequestFilter started for request URI: {}", request.getRequestURI());

        final String authorizationHeader = request.getHeader("Authorization");
        logger.debug("Authorization header: {}", authorizationHeader);

        String userNameOrEmail = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            logger.debug("Extracted JWT: {}", jwt);
            try {
                userNameOrEmail = jwtUtil.extractUsernameOrEmail(jwt);
                logger.info("Extracted username/email from token: {}", userNameOrEmail);
            } catch (Exception e) {
                logger.error("Error extracting username/email from JWT: {}", e.getMessage());
            }
        } else {
            logger.warn("Authorization header missing or does not start with 'Bearer '");
        }

        if (userNameOrEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.info("No existing authentication found in SecurityContext. Proceeding with token validation for user: {}", userNameOrEmail);

            UserResponseDto userResponseDto = null;
            try {
                userResponseDto = userService.findByUserNameOrEmail(userNameOrEmail);
                logger.info("User fetched from database: {}", userResponseDto);
            } catch (Exception e) {
                logger.error("Error fetching user from DB: {}", e.getMessage());
                chain.doFilter(request, response);
                return;
            }

            if (jwtUtil.validateToken(jwt, userResponseDto)) {
                logger.info("JWT validation successful for user: {}", userNameOrEmail);
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userResponseDto, null, userResponseDto.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.info("Authentication token set in SecurityContext for user: {}", userNameOrEmail);
            } else {
                logger.warn("JWT validation failed for user: {}", userNameOrEmail);
            }
        } else {
            if (userNameOrEmail == null) {
                logger.warn("Username/Email could not be extracted from token.");
            } else {
                logger.debug("Authentication already set in SecurityContext.");
            }
        }

        logger.debug("Continuing filter chain.");
        chain.doFilter(request, response);
        logger.info("JwtRequestFilter completed for request URI: {}", request.getRequestURI());
    }
}