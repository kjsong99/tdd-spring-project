package com.song.tddproject.tddproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.song.tddproject.tddproject.config.exception.RestApiException;
import com.song.tddproject.tddproject.entity.User;
import com.song.tddproject.tddproject.repository.UserRepository;
import com.song.tddproject.tddproject.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.transaction.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    Logger LOGGER = LoggerFactory.getLogger(UserControllerTest.class);
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(this.wac).build();
    }




    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserController userController;


    @Test
    @Transactional
    public void join_test_success() throws Exception {

        Map<String, String> input = new HashMap<>();
        // body에 json 형식으로 회원의 데이터를 넣기 위해서 Map을 이용한다.
        input.put("name", "test");
        input.put("phone", "010-7118-1209");
        input.put("address", "test");
        input.put("password", "test");
        input.put("username", "test1234");

        String body = objectMapper.writeValueAsString(input);

        mockMvc.perform(post("/api/v1/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(jsonPath("$.name").value("test"));

    }

    @Test
    @Transactional
    public void join_test_fail() throws Exception {

        Map<String, String> input = new HashMap<>();
        input.put("name", "test");
        input.put("phone", "010-71181-1209");
        input.put("address", "test");
        input.put("password", "test");
        input.put("username", "test1234");

        String body = objectMapper.writeValueAsString(input);

        mockMvc.perform(post("/api/v1/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}
