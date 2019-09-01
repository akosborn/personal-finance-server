package com.osbornandrew.personalfinance.security;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

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
