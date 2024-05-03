package org.andrey.controller;

import lombok.RequiredArgsConstructor;
import org.andrey.integration.service.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class MainControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findAllBooks() throws Exception {
        mockMvc.perform(get("/main")
                        .param("name", "Хоббит"))
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    @WithAnonymousUser
    void anonymousFindAllBooks() throws Exception {
        mockMvc.perform(get("/main")
                        .param("name", "Хоббит"))
                .andExpect(status().is2xxSuccessful());
    }
}