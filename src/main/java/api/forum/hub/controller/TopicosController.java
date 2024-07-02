package api.forum.hub.controller;

import api.forum.hub.domain.Topico;
import api.forum.hub.domain.dto.CadastroTopicoRequest;
import api.forum.hub.domain.dto.DetalhesTopicoResponse;
import api.forum.hub.service.TopicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid CadastroTopicoRequest request, UriComponentsBuilder uriComponentsBuilder) {
        var topico = service.cadastrarTopico(new Topico(request));

        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalhesTopicoResponse(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DetalhesTopicoResponse>> listar(@PageableDefault(sort = {"dataCriacao"}) Pageable paginacao){
        var page = service.listarTopicos(paginacao).map(DetalhesTopicoResponse::new);

        return ResponseEntity.ok(page);
    }
}
