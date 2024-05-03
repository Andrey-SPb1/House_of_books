package org.andrey.integration.controller;

import lombok.RequiredArgsConstructor;
import org.andrey.integration.service.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class MainControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findAllBooks() throws Exception {
        mockMvc.perform(get("/main")
                        .param("name", "name"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Name"))
                .andExpect(jsonPath("$[0].author").value("Author"));
    }
    @Test
    @WithAnonymousUser
    void anonymousFindAllBooks() throws Exception {
        mockMvc.perform(get("/main")
                        .param("name", "Хоббит"))
                .andExpect(status().is2xxSuccessful());
    }
}