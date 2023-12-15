package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exeception.ResourceNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public void deleteUserById(Integer id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public User findUserById(Integer id) throws ResourceNotFoundException {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    public Boolean isUserAlreadySaved(String username){
        return !this.userRepository.findByUsername(username).isEmpty();
    }


}
