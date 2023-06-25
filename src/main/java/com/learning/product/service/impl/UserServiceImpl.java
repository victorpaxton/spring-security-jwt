package com.learning.product.service.impl;

import com.learning.product.model.entity.User;
import com.learning.product.repository.UserRepository;
import com.learning.product.util.ValueMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public String addUser(User user) {
        log.info("UserService:addUser started");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.debug("UserService:addUser to database: {}", ValueMapper.jsonAsString(user));
        userRepository.save(user);
        log.info("UserService:addUser ended.");
        return "User added successfully";
    }
}
