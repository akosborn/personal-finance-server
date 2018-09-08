package com.osbornandrew.personal.finance.server.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class OAuthToken extends AbstractAuthenticationToken {

    @Getter @Setter
    private String provider;

    @Getter @Setter
    private String userId;

    @Getter @Setter
    private String name;

    public OAuthToken(String provider, String userId, String name,
                      Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.provider = provider;
        this.userId = userId;
        this.name = name;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return name;
    }
}
