package org.andrey.integration.controller;

import lombok.RequiredArgsConstructor;
import org.andrey.integration.service.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class BookControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/book/1"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void changeFavorites() throws Exception {
        mockMvc.perform(put("/book/1/favorites"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void changeBasket() throws Exception {
        mockMvc.perform(put("/book/1/basket"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void addReview() throws Exception {
        mockMvc.perform(post("/book/1/review")
                        .param("review", "test"))
                .andExpect(status().isCreated());
    }
}