package com.song.tddproject.tddproject.auth;

import com.song.tddproject.tddproject.entity.User;
import com.song.tddproject.tddproject.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class JwtAuthTest {

    @Autowired
    MockMvc mockMvc;

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
    } @Test
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
                .password("test")
                .address("test")
                .phone("test")
                .roles("ROLE_USER")
                .username("test1234")
                .build();

        return user;
    }

    private User createAdmin() throws Exception {
        User admin = User.builder()
                .name("test")
                .password("test")
                .address("test")
                .phone("test")
                .roles("ROLE_ADMIN")
                .username("test1234")
                .build();

        return admin;
    }

    @Test
    @Transactional
    public void admin_login_api_success() throws  Exception{

        User admin = createAdmin();

        mockMvc.perform(formLogin().user(admin.getUsername()).password("test")).andExpect(authenticated());

    }
    @Test
    @Transactional
    public void admin_login_api_fail() throws  Exception{
        User admin = createAdmin();

        mockMvc.perform(formLogin().user(admin.getUsername()).password("dest")).andExpect(unauthenticated());

    }

    @Test
    @Transactional
    public void user_login_api_success() throws Exception {

        User user = createUser();

        mockMvc.perform(formLogin().user(user.getUsername()).password("test")).andExpect(authenticated());

    }
    @Test
    @Transactional
    public void user_login_api_fail() throws Exception {

        User user = createUser();

        mockMvc.perform(formLogin().user(user.getUsername()).password("test")).andExpect(unauthenticated());

    }

}
