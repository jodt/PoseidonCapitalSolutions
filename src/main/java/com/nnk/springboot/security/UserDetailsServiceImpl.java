package com.nnk.springboot.security;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * This method is responsible for retrieving a user from the database based on the username
     * entered in the login form. If the user exists, a userDetails that provides core user information is return.
     * @param username
     * @return a userDetails that provides core user information.
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.get().getRole()));

        UserDetails userDetails = org.springframework.security.core.userdetails.User.
                withUsername(user.get().getUsername())
                .password(user.get().getPassword())
                .authorities(authorities)
                .build();

        return userDetails;
    }

}
