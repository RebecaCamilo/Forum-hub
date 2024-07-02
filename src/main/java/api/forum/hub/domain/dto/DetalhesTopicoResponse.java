package api.forum.hub.domain.dto;

import api.forum.hub.domain.Topico;

import java.time.LocalDateTime;

public record DetalhesTopicoResponse(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        Short status,
        String autor,
        String curso
) {
    public DetalhesTopicoResponse(Topico topico) {
        this(topico.getId(),
             topico.getTitulo(),
             topico.getMensagem(),
             topico.getDataCriacao(),
             topico.getStatus(),
             topico.getAutor(),
             topico.getCurso()
        );
    }
}
