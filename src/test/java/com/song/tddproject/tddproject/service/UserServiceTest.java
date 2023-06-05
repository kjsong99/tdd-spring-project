package com.song.tddproject.tddproject.service;

import com.song.tddproject.tddproject.entity.User;
import com.song.tddproject.tddproject.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @Transactional
    public void test_get_user(){
        Long id = userRepository.save(User.builder()
                .name("test")
                .password(bCryptPasswordEncoder.encode("test"))
                .address("test")
                .phone("test")
                .roles("ROLE_USER")
                .username("test1234")
                .build()).getId();

        User user = userService.getUser(id);

        assertThat(user).isNotNull();
    }

    @Test
    @Transactional
    public void test_get_all_users(){
        for (int i = 0 ; i < 10 ; i++){
            userRepository.save(User.builder()
                    .name("test " + Integer.toString(i))
                    .password(bCryptPasswordEncoder.encode("test"))
                    .address("test " + Integer.toString(i))
                    .phone("test" + Integer.toString(i))
                    .roles("ROLE_USER")
                    .username("test " + Integer.toString(i))
                    .build());
        }

        List<User> userList = userService.getAllUsers();

        assertThat(userList.size()).isEqualTo(10);
    }

    @Test
    @Transactional
    public void test_add_user(){
        User user = User.builder()
                .name("test")
                .password(bCryptPasswordEncoder.encode("test"))
                .address("test")
                .phone("test")
                .roles("ROLE_USER")
                .username("test1234")
                .build();

        User userEntity = userService.addUser(user);

        assertThat(userEntity).isNotNull();

    }

    @Test
    @Transactional
    public void test_user_exist_fail(){
        userRepository.save(User.builder()
                .name("test")
                .password(bCryptPasswordEncoder.encode("test"))
                .address("test")
                .phone("test")
                .roles("ROLE_USER")
                .username("test1234")
                .build()).getId();
        userService.validateUsername("test1234");


    }
    @Test
    public void test_user_exist_success(){
      userService.validateUsername("test1234");

    }

    @Test
    public void test_phone_validate_success(){
        String phone = "010-7118-1209";

   userService.validatePhone(phone);

    }

    @Test
    public void test_phone_validate_fail(){
        String phone = "010-71118-1209";
        userService.validatePhone(phone);

    }

    @Test
    @Transactional
    public void test_delete_user(){
        User user = userRepository.save(User.builder()
                .name("test")
                .password(bCryptPasswordEncoder.encode("test"))
                .address("test")
                .phone("test")
                .roles("ROLE_USER")
                .username("test1234")
                .build());

        userService.deleteUser(user);

        assertThat(userRepository.findById(user.getId())).isEmpty();

    }
//    필요한가? - 보통 username 변경은 지원하지 않는다
//    public void test_update_username(){
//
//    }

    @Test
    @Transactional
    public void test_update_name(){
        User user = userRepository.save(User.builder()
                .name("test")
                .password(bCryptPasswordEncoder.encode("test"))
                .address("test")
                .phone("test")
                .roles("ROLE_USER")
                .username("test1234")
                .build());

        userService.updateName(user, "newName");

        assertThat(userRepository.findById(user.getId()).orElseThrow().getName()).isEqualTo("newName");

    }

    @Test
    @Transactional
    public void test_update_address(){
        User user = userRepository.save(User.builder()
                .name("test")
                .password(bCryptPasswordEncoder.encode("test"))
                .address("test")
                .phone("test")
                .roles("ROLE_USER")
                .username("test1234")
                .build());

        userService.updateAddress(user, "newAddress");

        assertThat(userRepository.findById(user.getId()).orElseThrow().getName()).isEqualTo("newAddress");

    }

    @Test
    @Transactional
    public void test_update_phone(){
        User user = userRepository.save(User.builder()
                .name("test")
                .password(bCryptPasswordEncoder.encode("test"))
                .address("test")
                .phone("test")
                .roles("ROLE_USER")
                .username("test1234")
                .build());

        userService.updateAddress(user, "newPhone");

        assertThat(userRepository.findById(user.getId()).orElseThrow().getName()).isEqualTo("newPhone");

    }


}
