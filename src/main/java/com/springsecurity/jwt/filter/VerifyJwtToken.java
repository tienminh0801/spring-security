package com.springsecurity.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class VerifyJwtToken extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/api/refresh-token")) {
            filterChain.doFilter(request,response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            new ObjectMapper().writeValue(response.getOutputStream(), "Authorization header invalid");
            filterChain.doFilter(request,response);
            return;
        }
        String token = authHeader.replace("Bearer ","");
        Key key = Keys.hmacShaKeyFor("tienminhandtienminhandtienminhandtienminhandtienminhandtienminh".getBytes());
        try {
            var payload = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            var roles = (ArrayList<String>) payload.get("roles");
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    payload.getSubject(),
                    null,
                    roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request,response);
        } catch (Exception e) {
            // ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException
            new ObjectMapper().writeValue(response.getOutputStream(), "Token invalid : " + e.getMessage());
            response.setStatus(400);
        }

    }
}
