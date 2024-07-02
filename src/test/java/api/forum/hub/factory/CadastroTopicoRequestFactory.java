package api.forum.hub.factory;

import api.forum.hub.domain.Topico;
import api.forum.hub.domain.dto.CadastroTopicoRequest;

import java.time.LocalDateTime;

public class CadastroTopicoRequestFactory {

    public static CadastroTopicoRequest criaCadastroTopicoRequestCompleto() {
        return new CadastroTopicoRequest(
                "Título do post",
                "Mensagem sobre o tópico...",
                "Autor do Tópico",
                "Curso do tópico"
        );
    }
}
