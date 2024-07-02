package api.forum.hub.controller;

import api.forum.hub.domain.Topico;
import api.forum.hub.domain.dto.CadastroTopicoRequest;
import api.forum.hub.domain.dto.DetalhesTopicoResponse;
import api.forum.hub.service.TopicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarTopico(@RequestBody @Valid CadastroTopicoRequest request, UriComponentsBuilder uriComponentsBuilder) {
        var topico = service.cadastrarTopico(new Topico(request));

        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalhesTopicoResponse(topico));
    }
}
