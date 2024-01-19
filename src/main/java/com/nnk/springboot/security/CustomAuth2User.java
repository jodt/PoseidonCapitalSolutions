package com.nnk.springboot.security;

import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

/**
 * This class is used to customize our Oauth2user.
 * In our case, the username of our user connected to github being stored in the "login" attribute,
 * we retrieve this username by overriding the getname method
 *
 */
public class CustomAuth2User implements OAuth2User {

    private OAuth2User user;

    public CustomAuth2User(OAuth2User user) {
        this.user = user;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return user.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getName() {
        return  user.getAttribute("login");
    }

}
