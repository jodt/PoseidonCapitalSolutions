package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exeception.ResourceNotFoundException;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User saveUser(User user);

    void deleteUserById(Integer id);

    User findUserById(Integer id) throws ResourceNotFoundException;

    Boolean isUserAlreadySaved(String username);

}
