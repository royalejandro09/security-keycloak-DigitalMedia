package com.digitalmedia.users.repository;

import com.digitalmedia.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private IUserRepository userRepository;

    public User validateAndGetUser(String username) {
        return userRepository.validateAndGetUser(username);
    }

    public Optional<User> getUserExtra(String username) {
        return userRepository.getUserExtra(username);
    }

    public User saveUserExtra(User user) {
        return userRepository.saveUserExtra(user);
    }
}
