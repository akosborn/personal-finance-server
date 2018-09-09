package com.osbornandrew.personalfinance.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.osbornandrew.personalfinance.MyUserDetails;
import com.osbornandrew.personalfinance.SocialProvider;
import com.osbornandrew.personalfinance.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TokenLoginFilter extends GenericFilterBean {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;

        // Extract token from header
        final String tokenString = req.getHeader("Authorization");

        if (tokenString == null)
            throw new InsufficientAuthenticationException("Authentication failed due to missing token in header");

        final GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList("4146647642-k52cqa8q4csm2d866nl51ic4kc6hu5ve.apps.googleusercontent.com"))
                .build();

        try {
            GoogleIdToken idToken = verifier.verify(tokenString);

            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // TODO: 8/21/2018  
                // Check if ID in database
                // If so, retrieve from db and create auth object
                // If not, save to database

                MyUserDetails userDetails = new MyUserDetails(
                        new User((String) payload.get("name"),
                                payload.getEmail(),
                                payload.getSubject(),
                                SocialProvider.GOOGLE)
                );

                log.info("Created {} user {}",
                        userDetails.getUser().getProvider(),
                        userDetails.getUser().getDisplayName());
                
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                Authentication auth = new OAuthToken(userDetails, authorities, idToken);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
