package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exeception.ResourceNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    private User user;

    @BeforeEach
    void init(){
        user = User.builder()
                .fullname("TEST")
                .username("User")
                .password("Test123456789+")
                .build();
    }

    @Test
    @DisplayName("Should find all users")
    void shouldFindAllUsers() {

        when(this.userRepository.findAll()).thenReturn(List.of(user));

        List<User> result = this.userService.findAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(user,result.get(0));

        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Should save or update a user")
    void shouldSaveUser() {

        User userSaved = user;
        userSaved.setId(5);

        when(this.passwordEncoder.encode(user.getPassword())).thenReturn("newPassword");
        when(this.userRepository.save(user)).thenReturn(userSaved);

        User result = this.userService.saveUser(user);

        assertNotNull(result);
        assertEquals(result,userSaved);

        verify(userRepository).save(user);
        verify(passwordEncoder).encode("Test123456789+");
    }

    @Test
    @DisplayName("Should delete a user by id")
    void shouldDeleteUserById() {
        User userSaved = user;
        userSaved.setId(5);

        doNothing().when(this.userRepository).deleteById(5);

        this.userService.deleteUserById(5);

        verify(userRepository).deleteById(5);
    }

    @Test
    @DisplayName("Should find a user by id")
    void shouldFindUserById() throws ResourceNotFoundException {
        User userSaved = user;
        userSaved.setId(5);

        when(this.userRepository.findById(5)).thenReturn(Optional.of(userSaved));

        User result = this.userService.findUserById(5);

        assertNotNull(result);
        assertEquals(result, userSaved);

        verify(userRepository).findById(5);

    }

    @Test
    @DisplayName("Should not find a user by id -> ResourceNotFoundException")
    void shouldNotFindUserById() throws ResourceNotFoundException {
        User userSaved = user;
        userSaved.setId(5);

        when(this.userRepository.findById(10)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> this.userService.findUserById(10));

        verify(userRepository).findById(10);

    }

}