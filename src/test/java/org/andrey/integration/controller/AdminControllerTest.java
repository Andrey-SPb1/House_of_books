package org.andrey.integration.controller;

import lombok.RequiredArgsConstructor;
import org.andrey.database.entity.Role;
import org.andrey.integration.service.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class AdminControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findUser() throws Exception {
        mockMvc.perform(get("/admin/users/1")
                        .with(user("test@gmail.com").authorities(Role.ADMIN)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void userFindUser() throws Exception {
        mockMvc.perform(get("/admin/users/1")
                        .with(user("test@gmail.com").authorities(Role.USER)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void anonymousFindUser() throws Exception {
        mockMvc.perform(get("/admin/users/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createUser() throws Exception {
        mockMvc.perform(post("/admin/users").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                   "firstname": "string",
                                   "lastname": "string",
                                   "email": "string@gmail.com",
                                   "birthDate": "2024-05-03",
                                   "rawPassword": "stringst",
                                   "role": "USER"
                                 }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    void updateUser() throws Exception {
        mockMvc.perform(put("/admin/users/1").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                   "firstname": "string",
                                   "lastname": "string",
                                   "email": "string@gmail.com",
                                   "birthDate": "2024-05-03",
                                   "rawPassword": "stringst",
                                   "role": "USER"
                                 }
                                """))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/admin/users/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    void createBook() throws Exception {
        mockMvc.perform(multipart("/admin/books")
                        .param("name", "string")
                        .param("author", "string")
                        .param("genre", "string")
                        .param("description", "string")
                        .param("yearOfPublish", "0")
                        .param("pages", "0")
                        .param("pricePaper", "0")
                        .param("priceDigital", "0")
                        .param("inStock", "0"))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBook() throws Exception {
        mockMvc.perform(put("/admin/books/1").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "name": "string",
                                  "author": "string",
                                  "genre": "string",
                                  "description": "string",
                                  "yearOfPublish": 0,
                                  "pages": 0,
                                  "pricePaper": 0,
                                  "priceDigital": 0,
                                  "inStock": 0
                                }
                                """))
                .andExpect(status().is2xxSuccessful());
    }
}