package org.ra.atomidtesttask;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ra.atomidtesttask.presentation.http.user.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class RegistrationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @Order(1)
    @WithMockUser
    public void register() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("qwerty");
        request.setPassword("qwerty");

        mvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.ALL)
                        .content(mapper.writeValueAsString(request)))
                .andExpectAll(status().isCreated(),
                        jsonPath("$.username").value("qwerty"),
                        jsonPath("$.role").value("USER"));
    }

    @Test
    @Order(2)
    @WithMockUser(username = "ADMIN", roles = {"ADMIN"})
    public void tryGetRegisteredUserViaAdmin() throws Exception {
        mvc.perform(get("/users/qwerty"))
                .andExpectAll(status().isOk(),
                        jsonPath("$.username").value("qwerty"),
                        jsonPath("$.role").value("USER"));
    }

    @Test
    @Order(3)
    @WithMockUser(username = "qwerty")
    public void tryGetSelf() throws Exception {
        mvc.perform(get("/users/qwerty"))
                .andExpectAll(status().isOk(),
                        jsonPath("$.username").value("qwerty"),
                        jsonPath("$.role").value("USER"));
    }

    @Test
    @Order(4)
    @WithMockUser(username = "other_username")
    public void tryGetOtherUser() throws Exception {
        mvc.perform(get("/users/qwerty"))
                .andExpect(status().isForbidden());
    }

}
