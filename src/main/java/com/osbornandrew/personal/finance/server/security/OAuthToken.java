package com.osbornandrew.personal.finance.server.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;

public class OAuthToken extends AbstractAuthenticationToken {

    OAuthToken(Object principal,
               Collection<? extends GrantedAuthority> authorities,
               Object authToken) {
        super(authorities);
        this.principal = principal;
        this.authToken = authToken;
        setAuthenticated(true);
    }

    @Getter
    private final Object principal;
    private Object authToken;

    @Override
    public Object getCredentials() {
        return authToken;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
