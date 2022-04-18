package com.springsecurity.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Map<String,String> requestAuthenticationBody =
                    new ObjectMapper().readValue(request.getInputStream(),Map.class);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(requestAuthenticationBody.get("username"),
                            requestAuthenticationBody.get("password"));
            return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        Key key = Keys.hmacShaKeyFor("tienminhandtienminhandtienminhandtienminhandtienminhandtienminh".getBytes());

        String access_token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("roles", authResult.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .setIssuedAt(new Date())
                .setIssuer("TienMinh")
                .setExpiration(new Date(System.currentTimeMillis() + 30*60*1000))
                .signWith(key)
                .compact();

        String refesh_token = Jwts.builder()
                .setSubject(authResult.getName())
                .setIssuedAt(new Date())
                .setIssuer("TienMinh")
                .setExpiration(new Date(System.currentTimeMillis() + 24*60*60*1000))
                .signWith(key)
                .compact();

        var token = new HashMap<String,String>();

        token.put("access_token", access_token);
        token.put("refesh_token", refesh_token);

        new ObjectMapper().writeValue(response.getOutputStream(), token);

    }
}
