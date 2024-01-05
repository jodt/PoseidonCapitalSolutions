package com.nnk.springboot.security;

import com.nnk.springboot.utils.UserUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        if (UserUtils.isAdmin(authentication)) {
            String targetUrl = "/user/list";
            getRedirectStrategy().sendRedirect(request, response, targetUrl);

        } else {
            String targetUrl = "/bidList/list";
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }

    }

}


