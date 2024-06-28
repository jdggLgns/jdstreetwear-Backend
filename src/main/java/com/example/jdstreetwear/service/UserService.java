package com.example.jdstreetwear.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.jdstreetwear.dao.UserDAO;
import com.example.jdstreetwear.model.User;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userDAO.findById(id);
    }

    public User createUser(User user) {
        return userDAO.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        Optional<User> optionalUser = userDAO.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(userDetails.getFirstName());
            user.setLastName(userDetails.getLastName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setRole(userDetails.getRole());
            return userDAO.save(user);
        } else {
            throw new NoSuchElementException("User not found with id " + id);
        }
    }

    public void deleteUser(Long id) {
        userDAO.deleteById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userDAO.findByEmail(email);
    }
}
