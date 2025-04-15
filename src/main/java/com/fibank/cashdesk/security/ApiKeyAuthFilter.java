package com.fibank.cashdesk.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter that checks for a valid API key in every incoming HTTP request.
 * The API key is expected in the header defined by 'security.api-key-header'.
 * Responds with 401 Unauthorized if missing or invalid.
 */
@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(ApiKeyAuthFilter.class);

    @Value("${security.api-key}")
    private String expectedApiKey;

    @Value("${security.api-key-header}")
    private String apiHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String apiKey = request.getHeader(apiHeader);

        if (expectedApiKey.equals(apiKey)) {
            filterChain.doFilter(request, response);
        } else {
            logger.warn("Unauthorized access attempt. Missing or invalid API key from IP: {}", request.getRemoteAddr());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/plain");
            response.getWriter().write("Unauthorized: Invalid API key");
        }
    }
}
