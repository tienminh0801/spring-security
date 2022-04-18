package com.springsecurity.jwt.controller;

import com.springsecurity.jwt.entity.Role;
import com.springsecurity.jwt.entity.User;
import com.springsecurity.jwt.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommonController {
    private final UserService userService;

    @GetMapping("/user")
    @PreAuthorize("hasRole('MANAGER')")
    ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("user/info")
    ResponseEntity<? extends Object> getInfoUser(@RequestHeader("Authorization") String authHeader){
        if (authHeader == null) return ResponseEntity.status(400).body("Not found Authorization header");
        String token = authHeader.replace("Bearer ","");
        Key key = Keys.hmacShaKeyFor("tienminhandtienminhandtienminhandtienminhandtienminhandtienminh".getBytes());
        try {
            String username = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
            return ResponseEntity.ok().body(userService.getUser(username));
        } catch (Exception e){
            return ResponseEntity.status(400).body("Token invalid : " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/user/insert")
    ResponseEntity<User> insertUser(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.saveUser(user));
    }

    @PostMapping("/user/add-role-into-user")
    void addRoleIntoUser(String roleName, String username){
        userService.addRoleToUser(username,roleName);
    }

    @PostMapping("/role/insert")
    ResponseEntity<Role> insertRole(@RequestBody Role role){
        return ResponseEntity.ok().body(role);
    }

    @GetMapping("/refresh-token")
    ResponseEntity<? extends Object> getNewToken(@RequestHeader("Authorization") String authHeader){
        if (authHeader == null) return ResponseEntity.status(400).body("Not found Authorization header");
        String token = authHeader.replace("Bearer ","");
        Key key = Keys.hmacShaKeyFor("tienminhandtienminhandtienminhandtienminhandtienminhandtienminh".getBytes());
        try {
            String username = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
            User user = userService.getUser(username);
            String access_token = Jwts.builder()
                    .setSubject(user.getName())
                    .claim("roles", user.getRoles())
                    .setIssuedAt(new Date())
                    .setIssuer("TienMinh")
                    .setExpiration(new Date(System.currentTimeMillis() + 30*60*1000))
                    .signWith(key)
                    .compact();
            var returnToken = new HashMap<String,String>();

            returnToken.put("access_token", access_token);
            returnToken.put("refesh_token", token);

            return ResponseEntity.ok().body(returnToken);
        } catch (Exception e){
            return ResponseEntity.status(400).body("Token invalid : " + e.getMessage());
        }
    }

}
