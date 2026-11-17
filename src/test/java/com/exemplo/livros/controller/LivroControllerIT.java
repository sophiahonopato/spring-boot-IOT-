package com.exemplo.livros.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LivroControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private static final String JSON_VALIDO =
        "{\"titulo\":\"Capitães da Areia\",\"autor\":\"Jorge Amado\",\"anoPublicacao\":1937,\"preco\":42.5}";

    @Test
    void deveCriarELerLivroComAdmin() throws Exception {
        mockMvc.perform(post("/api/livros")
                .with(httpBasic("admin", "admin123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_VALIDO))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.titulo", is("Capitães da Areia")));

        mockMvc.perform(get("/api/livros").with(httpBasic("user", "user123")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    void deveRetornar400QuandoDadosInvalidos() throws Exception {
        mockMvc.perform(post("/api/livros")
                .with(httpBasic("admin", "admin123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"titulo\":\"\",\"autor\":\"X\",\"anoPublicacao\":2000,\"preco\":-1}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.mensagens", not(empty())));
    }

    @Test
    void deveRetornar404ParaIdInexistente() throws Exception {
        mockMvc.perform(get("/api/livros/9999").with(httpBasic("user", "user123")))
            .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar401SemAutenticacao() throws Exception {
        mockMvc.perform(get("/api/livros"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void deveRetornar403QuandoUserTentaCriar() throws Exception {
        mockMvc.perform(post("/api/livros")
                .with(httpBasic("user", "user123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_VALIDO))
            .andExpect(status().isForbidden());
    }
}
