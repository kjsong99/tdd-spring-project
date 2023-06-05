package com.song.tddproject.tddproject.dto;

import com.song.tddproject.tddproject.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@NoArgsConstructor
public class JoinRequestDto {

    public String username;

    public String password;

    public String name;

    public String address;

    public String phone;

    public User toEntity(){
        return User.builder()
                .username(username)
                .name(name)
                .password(password)
                .address(address)
                .phone(phone)
                .roles("ROLE_USER")
                .build();
    }
}
