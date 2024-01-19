package com.nnk.springboot.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.CollectionUtils;

public class UserUtils {

    public static Boolean isAdmin(Authentication authentication) {
        return !CollectionUtils.isEmpty(authentication.getAuthorities())
                && authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
    }

}
