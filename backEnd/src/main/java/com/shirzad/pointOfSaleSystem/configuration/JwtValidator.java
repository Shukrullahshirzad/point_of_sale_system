package com.shirzad.pointOfSaleSystem.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtValidator extends OncePerRequestFilter {

    @Override
    // This method is called for each HTTP request to validate the JWT token.
    // It extracts the JWT token from the request header and performs validation.
    // HttpServletRequest: represents the incoming HTTP request.
    // HttpServletResponse: represents the outgoing HTTP response.
    // FilterChain: allows the request to proceed to the next filter in the chain.
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        // Extract the JWT token from the request header.
        String jwtToken = request.getHeader(JwtConstants.JWT_HEADER);

        if (jwtToken != null) {
            // Perform JWT validation logic here (e.g., verify signature, expiration, etc.)
            // If valid, set the authentication in the security context.
            // If invalid, you can choose to send an error response or proceed without authentication.
            jwtToken = jwtToken.substring(7); // Remove "Bearer " prefix

            try{
                // Validate the JWT token using the secret key.
                // If the token is valid, you can extract claims or set authentication context here.
                SecretKey key = Keys.hmacShaKeyFor(JwtConstants.JWT_SECRET.getBytes());

                // Claims means the payload part of the JWT which contains the information about the user and other metadata.
                // Here we are parsing the JWT token to extract the claims.
                // parsing means converting the JWT token string into a structured format that we can work with.
                Claims claims = Jwts.parser()
                        // Set the secret key used for verifying the JWT signature.
                        .verifyWith(key).build()
                        //
                        .parseSignedClaims(jwtToken).getPayload();
                String email = String.valueOf(claims.get("email"));
                System.out.println("JWT is valid. Email: " + email);
                String authorities = String.valueOf(claims.get("authorities"));
                System.out.println("Authorities: " + authorities);

                List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(
                        authorities
                );
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        email, null, authorityList
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }catch (Exception e){
                throw new BadCredentialsException("Invalid JWT token: " + e.getMessage());
            }
        }
        filterChain.doFilter(request, response);

    }
}
