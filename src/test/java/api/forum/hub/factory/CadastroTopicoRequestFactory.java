package api.forum.hub.factory;

import api.forum.hub.domain.Topico;
import api.forum.hub.domain.dto.CadastroTopicoRequest;

import java.time.LocalDateTime;

public class CadastroTopicoRequestFactory {

    public static CadastroTopicoRequest criaCadastroTopicoRequestCompleto() {
        return new CadastroTopicoRequest(
                "Titulo do post",
                "Mensagem sobre o topico...",
                "Autor do Topico",
                "Curso do topico"
        );
    }
}
