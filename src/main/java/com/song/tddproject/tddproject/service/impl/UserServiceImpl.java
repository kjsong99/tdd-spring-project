package com.song.tddproject.tddproject.service.impl;

import com.song.tddproject.tddproject.entity.User;
import com.song.tddproject.tddproject.repository.UserRepository;
import com.song.tddproject.tddproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUser(Long id){
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public Long addUser(User user) {
        return userRepository.save(user).getId();
    }

    @Override
    public boolean validateUsername(String username) {
        return userRepository.findUserByUsername(username).isEmpty();
    }

    @Override
    public boolean validatePhone(String phone) {
        return Pattern.matches("^\\d{2,3}-\\d{3,4}-\\d{4}$", phone);
    }

    @Override
    public void deleteUser(User user) {
        try {
            userRepository.delete(user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public User updateName(User user, String newName) {
        user.setName(newName);
        try {
            userRepository.save(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    @Transactional
    public User updateAddress(User user, String newAddress) {
        user.setName(newAddress);
        try {
            userRepository.save(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    @Transactional
    public User updatePhone(User user, String newPhone) {
        user.setName(newPhone);
        try {
            userRepository.save(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

}
