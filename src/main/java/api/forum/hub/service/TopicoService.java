package api.forum.hub.service;

import api.forum.hub.domain.Topico;
import api.forum.hub.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {

    private Short ATIVO = (short) 1;

    @Autowired
    private TopicoRepository repository;

    public Topico cadastrarTopico(Topico topico) {
        return repository.save(topico);
    }

    public Page<Topico> listarTopicos(Pageable pageable) {
        return repository.findAllByStatus(ATIVO, pageable);
    }

    public Topico detalharTopico(Long id) {
        return repository.getReferenceById(id);
    }

    public Topico atualizarTopico(Topico topicoAtt) {
        var topico = repository.findById(topicoAtt.getId())
                .orElseThrow(() -> new IllegalArgumentException("Topico com o ID fornecido não foi encontrado"));
        topico.atualizarTopico(topicoAtt);
        return topico;
    }

    public void excluirTopico(Long id) {
        var topico = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Topico com o ID fornecido não foi encontrado"));
        topico.excluir();
    }
}
