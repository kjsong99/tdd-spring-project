package com.song.tddproject.tddproject.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.song.tddproject.tddproject.config.auth.JwtProperties;
import com.song.tddproject.tddproject.entity.User;
import com.song.tddproject.tddproject.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class JwtAuthTest {

    Logger LOGGER = LoggerFactory.getLogger(JwtAuthTest.class);

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Test
    @WithAnonymousUser
    public void index_anonymous() throws  Exception{
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "song", password = "1234", roles = "USER")
    public void index_user() throws  Exception{
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    public void index_admin() throws  Exception{
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void user_api_anonymous() throws  Exception{
        mockMvc.perform(get("/api/v1/user"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "song", password = "1234", roles = "USER")
    public void user_api_user() throws  Exception{
        mockMvc.perform(get("/api/v1/user"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    public void user_api_admin() throws  Exception{
        mockMvc.perform(get("/api/v1/user"))
                .andDo(print())
                .andExpect(status().isOk());
    } @Test
    @WithAnonymousUser
    public void admin_api_anonymous() throws  Exception{
        mockMvc.perform(get("/api/v1/admin"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "song", password = "1234", roles = "USER")
    public void admin_api_user() throws  Exception{
        mockMvc.perform(get("/api/v1/admin"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", password = "1234", roles = "ADMIN")
    public void admin_api_admin() throws  Exception{
        mockMvc.perform(get("/api/v1/admin"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private User createUser() throws Exception {
        User user = User.builder()
                .name("test")
                .password(bCryptPasswordEncoder.encode("test"))
                .address("test")
                .phone("test")
                .roles("ROLE_USER")
                .username("test1234")
                .build();

        return userRepository.save(user);

    }

    private User createAdmin() throws Exception {
        User admin = User.builder()
                .name("test")
                .password(bCryptPasswordEncoder.encode("test"))
                .address("test")
                .phone("test")
                .roles("ROLE_ADMIN")
                .username("test1234")
                .build();

        return userRepository.save(admin);

    }

    @Test
    @Transactional
    public void admin_login_api_success() throws  Exception{


        Map<String, String> input = new HashMap<>();
        // body에 json 형식으로 회원의 데이터를 넣기 위해서 Map을 이용한다.
        input.put("password", "test");
        input.put("username", "test1234");

        User admin = createAdmin();

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                ).andExpect(result -> {
                    result.getResponse().getHeader(JwtProperties.HEADER_STRING).contains("Bearer");
                })
                .andDo(print());

//        mockMvc.perform(formLogin().user(admin.getUsername()).password("test")).andExpect(authenticated()).andDo(print());
//          form login disable 해놔서 mockMvc 가 안 먹히는듯??

    }
    @Test
    @Transactional
    public void admin_login_api_fail() throws  Exception{
        Map<String, String> input = new HashMap<>();
        // body에 json 형식으로 회원의 데이터를 넣기 위해서 Map을 이용한다.
        input.put("password", "dest");
        input.put("username", "test1234");

        User admin = createAdmin();

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                ).andExpect(unauthenticated())
                .andDo(print());

    }

    @Test
    @Transactional
    public void user_login_api_success() throws Exception {

        Map<String, String> input = new HashMap<>();
        // body에 json 형식으로 회원의 데이터를 넣기 위해서 Map을 이용한다.
        input.put("password", "test");
        input.put("username", "test1234");

        User admin = createUser();

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                ).andExpect(result -> {
                    result.getResponse().getHeader(JwtProperties.HEADER_STRING).contains("Bearer");
                })
                .andDo(print());

    }
    @Test
    @Transactional
    public void user_login_api_fail() throws Exception {

        Map<String, String> input = new HashMap<>();
        // body에 json 형식으로 회원의 데이터를 넣기 위해서 Map을 이용한다.
        input.put("password", "test");
        input.put("username", "test1234");

        User admin = createUser();

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                ).andExpect(unauthenticated())
                .andDo(print());

    }

}
