package com.osbornandrew.personalfinance.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.osbornandrew.personalfinance.users.MyUserDetails;
import com.osbornandrew.personalfinance.users.MyUserService;
import com.osbornandrew.personalfinance.users.SocialProvider;
import com.osbornandrew.personalfinance.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
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
import java.util.Optional;

@Component
public class TokenLoginFilter extends GenericFilterBean {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private MyUserService userService;

    public TokenLoginFilter(MyUserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;

        // Extract token from header
        final String tokenString = req.getHeader("Authorization");

        if (tokenString == null) {
            log.error("'Authorization' header missing in request");
            throw new InsufficientAuthenticationException("Authentication failed due to missing token in header");
        }

        // TODO: 9/9/2018 Check provider of auth token once other providers are added

        final GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList("4146647642-k52cqa8q4csm2d866nl51ic4kc6hu5ve.apps.googleusercontent.com"))
                .build();

        try {
            GoogleIdToken idToken = verifier.verify(tokenString);

            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Retrieve user from database. Create and save if not found.
                MyUserDetails userDetails = new MyUserDetails(
                        userService.loadBySocialIdAndProvider(payload.getSubject(), SocialProvider.GOOGLE)
                                .orElseGet(
                                        () -> {
                                            User user = userService.save(new User(
                                                    (String) payload.get("name"),
                                                    payload.getEmail(),
                                                    payload.getSubject(),
                                                    SocialProvider.GOOGLE));
                                            log.info("Registered {} user {} (Social ID {})",
                                                    user.getProvider(),
                                                    user.getDisplayName(),
                                                    user.getSocialId());
                                            return user;
                                        })
                );
                
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
