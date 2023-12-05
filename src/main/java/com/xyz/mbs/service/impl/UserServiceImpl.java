package com.xyz.mbs.service.impl;

import com.xyz.mbs.exceptions.RecordNotFoundException;
import com.xyz.mbs.model.User;
import com.xyz.mbs.repository.UserRepository;
import com.xyz.mbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User newUser) {
        return userRepository.save(newUser);
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(RecordNotFoundException::new);
    }

    public User updateUser(Long userId, User updatedUser) {
        return userRepository.save(updatedUser);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
