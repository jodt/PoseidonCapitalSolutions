package com.nnk.springboot.security;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class Oauth2UserService extends DefaultOAuth2UserService {

    /**
     * This class is use to obtain the user attributes of the resource owner
     * @param oAuth2UserRequest
     * @return an AuthenticatedPrincipal in the form of an OAuth2User
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        return new CustomAuth2User(super.loadUser(oAuth2UserRequest));
    }

}
