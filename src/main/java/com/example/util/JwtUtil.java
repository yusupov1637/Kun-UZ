package com.example.util;

import com.example.dto.auth.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.exceptions.TokenNotValidException;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;

public class JwtUtil {
    private static final String secretKey = "mazgi";
    private static final int tokenLiveTime = 1000 * 60 * 60
            ;

    public static String encode(Integer profileId, ProfileRole profileRole) {
        JwtBuilder jwtBuilder = Jwts.builder();

        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);

        jwtBuilder.claim("id", profileId);
        jwtBuilder.claim("role", profileRole);

        int tokenLiveTime = 1000 * 60 * 60 * 24;
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + tokenLiveTime));
        jwtBuilder.setIssuer("Muhammadsodiq");
        return jwtBuilder.compact();
    }
    public static JwtDTO decode(String token) {
        JwtParser jwtParser = Jwts.parser();
        jwtParser.setSigningKey(secretKey);

        Jws<Claims> jws = jwtParser.parseClaimsJws(token);

        Claims claims = jws.getBody();

        Integer id = (Integer) claims.get("id");
        String role = (String) claims.get("role");

        ProfileRole profileRole = ProfileRole.valueOf(role);

        return new JwtDTO(id, profileRole);
    }
    public static Integer getIdFromHeader(HttpServletRequest request, ProfileRole role) {
        Integer id = (Integer) request.getAttribute("id");
        ProfileRole jwtRole = (ProfileRole) request.getAttribute("role");
        if (!jwtRole.equals(role)) {
            throw new TokenNotValidException("Method Not Allowed");
        }
        return id;
    }
    public static Integer getIdFromHeader(HttpServletRequest request) {
        try {
            return (Integer) request.getAttribute("id");
        } catch (RuntimeException e) {
            throw new TokenNotValidException("Not Authorized");
        }
    }

    public static JwtDTO getJwtDTO(HttpServletRequest request) {
        try {
            Integer id = (Integer) request.getAttribute("id");
            ProfileRole role = (ProfileRole) request.getAttribute("role");

            return new JwtDTO(id, role);
        } catch (RuntimeException e) {
            throw new TokenNotValidException("Not Authorized");
        }
    }





    public static String encode(Integer profileId) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);

        jwtBuilder.claim("id", profileId);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.setIssuer("Muhammadsodiq");

        return jwtBuilder.compact();
    }
    public static Integer decodeForEmailVerification(String token) {
        JwtParser jwtParser = Jwts.parser();
        jwtParser.setSigningKey(secretKey);

        Jws<Claims> jws = jwtParser.parseClaimsJws(token);

        Claims claims = jws.getBody();

        Integer id = (Integer) claims.get("id");
        return id;
    }
}
