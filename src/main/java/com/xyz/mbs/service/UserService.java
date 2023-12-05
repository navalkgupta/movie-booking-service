package com.xyz.mbs.service;

import com.xyz.mbs.model.Customer;
import com.xyz.mbs.model.TheatrePartner;
import com.xyz.mbs.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> getAllUsers();

    User addUser(User user);

    User getUser(Long userId);

    User updateUser(Long userId, User updatedUser);

    void deleteUser(Long userId);
}
