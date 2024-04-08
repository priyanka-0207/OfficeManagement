package com.task.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenValidCredentials_whenRequestSecuredEndpoint_thenSuccess() throws Exception {
        mockMvc.perform(get("/secured")
                .with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(content().string("This is a secured endpoint!"));
    }

    @Test
    public void givenInvalidCredentials_whenRequestSecuredEndpoint_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/secured")
                .with(httpBasic("invalidUser", "invalidPassword")))
                .andExpect(status().isUnauthorized());
    }
}

