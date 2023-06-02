package com.song.tddproject.tddproject.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class JwtAuthTest {

    @Autowired
    MockMvc mockMvc;

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
}
