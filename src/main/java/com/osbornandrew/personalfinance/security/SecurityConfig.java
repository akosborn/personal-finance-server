package com.osbornandrew.personalfinance.security;

import com.osbornandrew.personalfinance.users.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private MyUserService userService;

    @Autowired
    public SecurityConfig(MyUserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // TODO: 8/19/2018 Remove before deploying
        http
                .cors() // TODO: 9/6/2018 Remove before deploying. Find way to host
                .and()
                .authorizeRequests()
                .anyRequest().authenticated() // Every request requires authentication
                .and()
                .addFilterBefore(new TokenLoginFilter(userService), BasicAuthenticationFilter.class);
    }
}
