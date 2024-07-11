package api.forum.hub.controller;

import api.forum.hub.domain.Topico;
import api.forum.hub.domain.dto.AtualizacaoTopicoRequest;
import api.forum.hub.domain.dto.CadastroTopicoRequest;
import api.forum.hub.domain.dto.DetalhesTopicoResponse;
import api.forum.hub.service.TopicoService;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static api.forum.hub.factory.AtualizacaoTopicoRequestFactory.criaAtualizacaoTopicoRequestCompleto;
import static api.forum.hub.factory.AtualizacaoTopicoRequestFactory.criaAtualizacaoTopicoRequestInvalido;
import static api.forum.hub.factory.CadastroTopicoRequestFactory.criaCadastroTopicoRequestCompleto;
import static api.forum.hub.factory.CadastroTopicoRequestFactory.criaCadastroTopicoRequestInvalido;
import static api.forum.hub.factory.DetalhesTopicoResponseFactory.criaDetalhesTopicoResponseCompleto;
import static api.forum.hub.factory.TopicoFactory.criaTopicoCompleto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TopicosControllerTest {

    private final Long ID = 1L;

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<CadastroTopicoRequest> cadastroTopicoRequestJson;
    @Autowired
    private JacksonTester<DetalhesTopicoResponse> detalhesTopicoResponseJson;
    @Autowired
    private JacksonTester<AtualizacaoTopicoRequest> atualizacaoTopicoRequestJson;
    @MockBean
    private TopicoService service;

    @Test
    @WithMockUser
    @DisplayName("Deve retornar 201 quando cadastra topico com sucesso")
    void deveRetornar201QuandoCadastraTopicoComSucesso() throws Exception {
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
    @WithMockUser
    @DisplayName("Deve retornar 400 quando cadastra topico invalido")
    void deveRetornar400QuandoCadastraTopicoInvalido() throws Exception {
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

    @Test
    @WithMockUser
    @DisplayName("Deve retornar 200 quando listar e houver topico cadastrado")
    void deveRetornar200QuandoListarEHouverTopicoCadastrado() throws Exception {
        // Given
        List<Topico> topicosCadastrados = Arrays.asList(criaTopicoCompleto());
        Page<Topico> pageComTopico = new PageImpl<>(topicosCadastrados, Pageable.unpaged(), topicosCadastrados.size());

        // When
        when(service.listarTopicos(any())).thenReturn(pageComTopico);

        var response = mvc.perform(get("/topicos"))
                .andReturn()
                .getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("\"numberOfElements\":1");
        System.out.println(response.getContentAsString());
    }

    @Test
    @WithMockUser
    @DisplayName("Deve retornar 200 quando listar e não houver topico cadastrado")
    void deveRetornar200QuandoListarENaoHouverTopicoCadastrado() throws Exception {
        // Given
        List<Topico> emptyList = new ArrayList<>();
        Pageable pageable = Pageable.unpaged();
        Page<Topico> emptyPage = new PageImpl<>(emptyList, pageable, 0);

        // When
        when(service.listarTopicos(any())).thenReturn(emptyPage);

        var response = mvc.perform(get("/topicos"))
                .andReturn()
                .getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("\"numberOfElements\":0");
        System.out.println(response.getContentAsString());
    }

    @Test
    @WithMockUser
    @DisplayName("Deve retornar 200 quando existir topico com id especificado")
    void deveRetornar200QuandoExistirTopicoComIdEspecificado() throws Exception {
        // Given
        var topico = criaTopicoCompleto();

        // When
        when(service.detalharTopico(any())).thenReturn(topico);

        var response = mvc
                .perform(get("/topicos/{id}", topico.getId()))
                .andReturn().getResponse();

        var jsonEsperado = detalhesTopicoResponseJson.write(
                criaDetalhesTopicoResponseCompleto()
        ).getJson();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @WithMockUser
    @DisplayName("Deve retornar 404 quando nao existir topico com id especificado")
    void deveRetornar404QuandoNaoExistirTopicoComIdEspecificado() throws Exception {
        // When
        when(service.detalharTopico(ID)).thenThrow(createObjectNotFoundException(ID));

        mvc.perform(get("/topicos/" + ID))
                .andExpect(status().isNotFound());

        // Then
        verify(service, times(1)).detalharTopico(ID);
    }

    @Test
    @WithMockUser
    @DisplayName("Deve retornar 200 quando atualizar topico com sucesso")
    void deveRetornar200QuandoAtualizarTopicoComSucesso() throws Exception {
        // When
        when(service.atualizarTopico(any())).thenReturn(criaTopicoCompleto());

        var response = mvc
                .perform(
                        put("/topicos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(atualizacaoTopicoRequestJson.write(criaAtualizacaoTopicoRequestCompleto())
                                        .getJson())
                )
                .andReturn().getResponse();

        var jsonEsperado = detalhesTopicoResponseJson.write(
                criaDetalhesTopicoResponseCompleto()
        ).getJson();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @WithMockUser
    @DisplayName("Deve retornar 400 quando tenta atualizar topico invalido")
    void deveRetornar400QuandoTentaAtualizarTopicoInvalido() throws Exception {
        // When
        var response = mvc
                .perform(
                        put("/topicos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(atualizacaoTopicoRequestJson.write(criaAtualizacaoTopicoRequestInvalido())
                                        .getJson())
                )
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser
    @DisplayName("Deve retornar 404 ao tentar atualizar quando nao existir topico com id especificado")
    void deveRetornar404AoTentarAtualizarQuandoNaoExistirTopicoComIdEspecificado() throws Exception {
        // When
        when(service.atualizarTopico(any())).thenThrow(createObjectNotFoundException(ID));

        mvc.perform(put("/topicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(atualizacaoTopicoRequestJson.write(criaAtualizacaoTopicoRequestCompleto())
                        .getJson()))
                .andExpect(status().isNotFound());

        // Then
        verify(service, times(1)).atualizarTopico(any());
    }

    @Test
    @WithMockUser
    @DisplayName("Deve retornar 204 quando nao excluir topico com sucesso")
    void deveRetornar204QuandoNaoExcluirTopicoComSucesso() throws Exception {
        // When
        doNothing().when(service).excluirTopico(ID);

        mvc.perform(delete("/topicos/{id}", ID))
                .andExpect(status().isNoContent());

        // Then
        verify(service, times(1)).excluirTopico(ID);
    }

    @Test
    @WithMockUser
    @DisplayName("Deve retornar 404 ao tentar excluir quando nao existir topico com id especificado")
    void deveRetornar404AoTentarExcluirQuandoNaoExistirTopicoComIdEspecificado() throws Exception {
        // When
        doThrow(createObjectNotFoundException(ID)).when(service).excluirTopico(ID);

        mvc.perform(delete("/topicos/{id}", ID))
                .andExpect(status().isNotFound());

        // Then
        verify(service, times(1)).excluirTopico(ID);
    }

    private static ObjectNotFoundException createObjectNotFoundException(Long id) {
        return new ObjectNotFoundException(id, "Topico com o ID fornecido não foi encontrado");
    }
}
