package com.blog.security;

import com.blog.config.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
//import java.util.function.Function;

@Component
public class JwtTokenHelper {

    // Retrieve Username from Jwt Token
    public String getUsernameFromToken(String token){
        return getAllClaimsFromToken(token).getSubject();
        //return getClaimFromToken(token,Claims::getSubject);
    }

    // Retrieve Expiration Date From Jwt Token
    public Date getExpirationDateFromToken(String token){
        return getAllClaimsFromToken(token).getExpiration();
        //return getClaimFromToken(token,Claims::getExpiration);
    }

//    public <T> T getClaimFromToken(String token,Function<Claims,T> claimsResolver){
//        final Claims claims = getAllClaimsFromToken(token);
//        return claimsResolver.apply(claims);
//    }

    // For Retrieving Any Information  From Token We Will Need The Secret Key
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(ApplicationConstants.SECRET).parseClaimsJws(token).getBody();
    }

    // Check If The Token HAs Expired
    private boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Generate Token For User
    public String generateToken(String subject){
        return Jwts
                .builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ApplicationConstants.JWT_TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS512,ApplicationConstants.SECRET)
                .compact();
    }

    // Validate Token
    public boolean validateToken(String token){
        return (!isTokenExpired(token));
    }

}
