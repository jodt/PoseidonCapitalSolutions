package com.nnk.springboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * This method is used to configure the security filter chain which is responsible
     * for all security (protection of application URLs, redirection to the login form, etc.)
     * @param http
     * @return a securityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/css/**").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers(("/user/**")).hasAuthority("ADMIN")
                                .anyRequest().authenticated()
                        )
                .userDetailsService(this.userDetailsService)
                .formLogin((login) -> login
                        .successHandler(successHandler()))
                .logout(Customizer.withDefaults());
        return http.build();
    }

    /**
     * This method is used to encode the password with Bcrypt
     * @return new instance of BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Ths method is used to handle redirection in case of successful connection
     * @return new instance of CustomSuccessHandler
     */
    @Bean
    public CustomSuccessHandler successHandler(){
        return new CustomSuccessHandler();
    }

}
