package api.forum.hub.factory;

import api.forum.hub.domain.dto.AtualizacaoTopicoRequest;
import api.forum.hub.domain.dto.CadastroTopicoRequest;

public class AtualizacaoTopicoRequestFactory {

    public static AtualizacaoTopicoRequest criaAtualizacaoTopicoRequestCompleto() {
        return new AtualizacaoTopicoRequest(
                1L,
                "Titulo do post",
                "Mensagem sobre o topico...",
                "Autor do Topico",
                "Curso do topico"
        );
    }

    public static AtualizacaoTopicoRequest criaAtualizacaoTopicoRequestInvalido() {
        return new AtualizacaoTopicoRequest(
                1L,
                "",
                "Mensagem sobre o topico...",
                "Autor do Topico",
                "Curso do topico"
        );
    }
}
