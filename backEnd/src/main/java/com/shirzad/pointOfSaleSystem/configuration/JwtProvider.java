package com.shirzad.pointOfSaleSystem.configuration;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtProvider {
    static SecretKey key =
            Keys.hmacShaKeyFor(JwtConstants.JWT_SECRET.getBytes());

    // This method generates a JWT token based on the provided authentication information.
    // Authentication: represents the authenticated user's details, including username and authorities.
    // It returns a JWT token as a String.
    public String generateJwtToken(Authentication authentication) {

        // Get the authorities (roles/permissions) associated with the authenticated user.
        // Collection<? extends GrantedAuthority> authorities= authentication.getAuthorities(); this line gets the
        // list of roles or permissions assigned to the user and stores them in the 'authorities'
        // variable as a collection of GrantedAuthority objects. Collection<? extends GrantedAuthority>. ? in this context means
        // that the collection can contain objects of any class that implements the GrantedAuthority interface.
        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();

        // Convert the collection of authorities into a comma-separated string.
        // This string representation of roles will be included in the JWT token.
        String roles = populateAuthorities(authorities);

        // Build and return the JWT token with claims and expiration.
        return Jwts.builder().issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 86400000)) // 1 day expiration
                .claim("email", authentication.getName()) // Set the email claim to the username of the authenticated user.
                .claim("authorities", roles) // Set the authorities claim to the comma-separated string of roles.
                .signWith(key) // Sign the JWT token using the secret key.
                .compact(); // Compact the JWT token into a String format.
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }

    public String getEmailFromToken(String token){

        // Parse the JWT token to extract claims.
        Claims claims = Jwts.parser()
                .verifyWith(key) // Set the secret key used for verifying the JWT signature.
                .build() // Build the parser. build means to create the parser instance with the specified configurations.
                .parseSignedClaims(token) //
                .getPayload(); // Get the payload (claims) from the parsed JWT token.

        return String.valueOf(claims.get("email"));

    }
}
