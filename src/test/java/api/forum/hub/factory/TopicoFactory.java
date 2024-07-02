package api.forum.hub.factory;

import api.forum.hub.domain.Topico;

import java.time.LocalDateTime;

public class TopicoFactory {

    public static Topico criaTopicoCompleto() {
        return new Topico(
                1L,
                "Título do post",
                "Mensagem sobre o tópico...",
                LocalDateTime.now(),
                (short) 1,
                "Autor do Tópico",
                "Curso do tópico"
        );
    }
}
