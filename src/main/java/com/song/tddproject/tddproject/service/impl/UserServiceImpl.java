package com.song.tddproject.tddproject.service.impl;

import com.song.tddproject.tddproject.config.exception.RestApiException;
import com.song.tddproject.tddproject.config.exception.UserErrorCode;
import com.song.tddproject.tddproject.entity.User;
import com.song.tddproject.tddproject.repository.UserRepository;
import com.song.tddproject.tddproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

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
    public User addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void validateUsername(String username) {
        if (!userRepository.findUserByUsername(username).isEmpty()){
            throw new RestApiException(UserErrorCode.INACTIVE_USER);
        }

    }

    @Override
    public void validatePhone(String phone) {
        if( !Pattern.matches("^\\d{2,3}-\\d{3,4}-\\d{4}$", phone)) {
            throw new RestApiException(UserErrorCode.INACTIVE_USER);
        }

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
