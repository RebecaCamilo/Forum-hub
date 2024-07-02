package api.forum.hub.service;

import api.forum.hub.domain.Topico;
import api.forum.hub.domain.dto.DetalhesTopicoResponse;
import api.forum.hub.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository repository;

    public Topico cadastrarTopico(Topico topico) {
        return repository.save(topico);

    }

//    var topico = service.cadastrarTopico(new Topico(request));
//
//    var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
//
//        return ResponseEntity.created(uri).body(new DetalhesTopicoResponse(topico));
}
