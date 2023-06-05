package com.song.tddproject.tddproject.service;

import com.song.tddproject.tddproject.entity.User;

import java.util.List;

public interface UserService {
    public User getUser(Long id);
    public List<User> getAllUsers();
    public Long addUser(User user);
    public boolean validateUsername(String username);
    public boolean validatePhone(String phone);
    public void deleteUser(User user);
    public User updateName(User user, String newName);
    public User updateAddress(User user, String newAddress);
    public User updatePhone(User user, String newPhone);

}
