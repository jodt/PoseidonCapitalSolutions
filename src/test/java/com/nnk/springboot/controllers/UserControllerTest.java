package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "john@test.com", authorities = {"ADMIN"})
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    private User user;

    @BeforeEach
    void init() {

        user = User.builder()
                .username("admin")
                .fullname("fullName")
                .role("ADMIN")
                .password("Admin123456789+")
                .build();
    }

    @Test
    @DisplayName("Should display the user list")
    void shouldDisplayUserListPage() throws Exception {

        when(this.userService.findAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", hasSize(1)))
                .andExpect(model().attribute("users", allOf(hasItem(user))))
                .andExpect(view().name("user/list"));

        verify(userService).findAllUsers();
    }

    @Test
    @DisplayName("Should display the add user form")
    void shouldDisplayAddUserForm() throws Exception {

        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    @DisplayName("Should save the user and redirect to the user listing page")
    void shouldValidateAndSaveAUser() throws Exception {

        mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .flashAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    @Test
    @DisplayName("Should not save the user -> user has error fields")
    void shouldNotValidateAndSaveAUser() throws Exception {

        mockMvc.perform(post("/user/validate")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeHasFieldErrors("user", "fullname", "username", "password", "role"));
    }

    @Test
    @DisplayName("Should display the user update form with the user to be updated")
    void shouldShowUpdateForm() throws Exception {

        when(this.userService.findUserById(1)).thenReturn(user);

        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attribute("user", user));

        verify(userService).findUserById(1);
    }

    @Test
    @DisplayName("Should update the user and redirect to the user listing page")
    void shouldUpdateUser() throws Exception {

        mockMvc.perform(post("/user/update/1")
                        .with(csrf())
                        .flashAttr("user", user))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    @Test
    @DisplayName("Should not update the user -> user has error fields")
    void shouldNotUpdateUser() throws Exception {

        mockMvc.perform(post("/user/update/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeHasFieldErrors("user", "fullname", "username", "password", "role"));
    }

    @Test
    @DisplayName("Should delete the user and redirect to the user listing page")
    void shouldDeleteUser() throws Exception {

        doNothing().when(this.userService).deleteUserById(1);

        mockMvc.perform(get("/user/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(userService).deleteUserById(1);
    }


    @Test
    @DisplayName("should not allow access to the user list page -> 403 forbidden")
    @WithMockUser(username = "john@test.com", authorities = {"USER"})
    void shouldNotDisplayUserListPage() throws Exception {


        mockMvc.perform(get("/user/list"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "john@test.com", authorities = {"USER"})
    @DisplayName("Should save the user and redirect to the user listing page -> forbidden")
    void shouldNotValidateAndSaveAUserForbidden() throws Exception {

        mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .flashAttr("user", user))
                .andExpect(status().isForbidden());
    }

}