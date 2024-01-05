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

    /**
     * This method is used to redirect the user after a successful login
     * based on their authorities (USER or ADMIN)
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     */
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


