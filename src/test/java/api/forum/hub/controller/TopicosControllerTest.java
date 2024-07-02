package api.forum.hub.controller;

import api.forum.hub.domain.dto.CadastroTopicoRequest;
import api.forum.hub.domain.dto.DetalhesTopicoResponse;
import api.forum.hub.service.TopicoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static api.forum.hub.factory.CadastroTopicoRequestFactory.criaCadastroTopicoRequestCompleto;
import static api.forum.hub.factory.CadastroTopicoRequestFactory.criaCadastroTopicoRequestInvalido;
import static api.forum.hub.factory.DetalhesTopicoResponseFactory.criaDetalhesTopicoResponseCompleto;
import static api.forum.hub.factory.TopicoFactory.criaTopicoCompleto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TopicosControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<CadastroTopicoRequest> cadastroTopicoRequestJson;
    @Autowired
    private JacksonTester<DetalhesTopicoResponse> detalhesTopicoResponseJson;
    @MockBean
    private TopicoService service;

    @Test
    @DisplayName("Deve retornar 201 quando cadastra topico com sucesso")
    void deveRestornar201QuandoCadastraTopicoComSucesso() throws Exception {
        // When
        when(service.cadastrarTopico(any())).thenReturn(criaTopicoCompleto());

        var response = mvc
                .perform(
                        post("/topicos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(cadastroTopicoRequestJson.write(criaCadastroTopicoRequestCompleto())
                                        .getJson())
                )
                .andReturn().getResponse();

        var jsonEsperado = detalhesTopicoResponseJson.write(
                criaDetalhesTopicoResponseCompleto()
        ).getJson();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deve retornar 400 quando cadastra topico invalido")
    void deveRestornar400QuandoCadastraTopicoInvalido() throws Exception {
        // When
        var response = mvc
                .perform(
                        post("/topicos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(cadastroTopicoRequestJson.write(criaCadastroTopicoRequestInvalido())
                                        .getJson())
                )
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}